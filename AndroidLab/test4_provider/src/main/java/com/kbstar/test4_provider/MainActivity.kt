package com.kbstar.test4_provider

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.util.Log
import android.provider.MediaStore
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.kbstar.test4_provider.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var contactsLauncher: ActivityResultLauncher<Intent>    // 주소록 목록화면 요청자
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>  // 퍼미션 설정 요청자

    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. 다이얼로그 띄우기
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()    // 요청 처리자
        ) {
            // 요청 후 콜백 (사후처리: 요청 처리자에서 사용자가 허용했는지 거부했는지 받아서 처리) -> 퍼미션 후 다이얼로그 닫힌 후 호출됨
            for(entry in it.entries) {
                if(entry.key == "android.permission.READ_CONTACTS" && entry.value) {
                    // 3.주소록 띄우기
                    // 퍼미션이 유저에 의해 허용 -> 주소록의 목록 activity를 Intent로 실행시킨다
                    val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                    contactsLauncher.launch(intent)  // 주소록 목록화면 띄우기
                } else {
                    // 퍼미션이 유저에 의해 거부
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        contactsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()    // 요청 처리자, intent 발생자
        ) {
            // 4. 목록에서 선택 후 되돌아옴
            // 주소록 목록화면에서 되돌아왔을 때 콜백 (사후처리)
            if(it.resultCode == RESULT_OK) {
                // 유저가 하나를 선택해서 되돌아온 경우, 선택한 사람의 식별자를 조건으로 구체적으로 원하는 데이터를 주소록 provider에게 다시 요청
                val cursor = contentResolver.query(
                    it!!.data!!.data!!, // 여기에 주소록 provider 지정하는 url, 선택한 사람 식별자 포함되어있음
                    arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null, null, null
                )
                if(cursor!!.moveToFirst()) {
                    // 넘어온 데이터가 있다면 -> 화면에 출력!
                    binding.contactResultView.text = "name : ${cursor?.getString(0)}, phone : ${cursor?.getString(1)}"
                }
            }
        }

        // 1. 유저가 버튼 누르기
        binding.contactButton.setOnClickListener {
            // 주소록 권한이 부여되어있는지 확인
            if(ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS")
                != PackageManager.PERMISSION_GRANTED) {
                // 거부 -> 부여해달라고 유저에게 요청
                permissionLauncher.launch(arrayOf("android.permission.READ_CONTACTS"))
            } else {
                // 허용
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                contactsLauncher.launch(intent)
            }
        }

        /////////// 갤러리 앱 연동
        val galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // 갤러리에서 돌아왔을 때 사후처리
            try {
                // BitmapFactory 작업 옵션..
                val option = BitmapFactory.Options()
                option.inSampleSize = 10    // 1/10로 줄여서 이미지 로딩

                // 갤러리 앱의 provider가 이미지를 읽을 수 있는 InputStream을 제공
                val inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()

                bitmap?.let {
                    //  읽은 이미지가 있다면, 화면에 출력
                    binding.userImageView.setImageBitmap(bitmap)
                }?: let {
                    // 읽은게 없다면
                    Log.d("", "no Image...")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }

        /////////// 카메라 앱 연동
        val cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // 카메라 앱이 저장한 파일 읽어서 화면에 출력
            val option = BitmapFactory.Options()
            option.inSampleSize = 10

            // 파일에서 이미지 로딩
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                // 이미지가 있다면.. 화면 출력
                binding.userImageView.setImageBitmap(bitmap)
            }   // 없으면 아무것도 안함
        }

        binding.cameraButton.setOnClickListener {
            // 파일 준비
            val tempStamp = SimpleDateFormat("yyyyMMdd_HHmmss")

            // 외장메모리 공간 > 앱별 저장소에 사진파일을 저장한다.
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${tempStamp}_", ".jpg", storageDir)
            filePath = file.absolutePath    // 나중에 읽어들여야하니까..

            // 카메라 앱에 공유하기 위한 파일 정보
            val photoURI = FileProvider.getUriForFile(this,"com.kbstar.test4_provider", file)

            // 카메라 앱 실행
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraLauncher.launch(intent)
        }
    }
}