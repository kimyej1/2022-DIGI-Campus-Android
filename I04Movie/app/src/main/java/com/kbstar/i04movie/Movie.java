package com.kbstar.i04movie;

public class Movie {
    String rnum, rank, rankInten, rankOldAndNew;
    String movieCd, movieNm, openDt;
    String salesAmt, salesShare, salesInten, salesChange, salesAcc;
    String audiCnt, audiInten, audiChange, audiAcc;
    String scrnCnt, showCnt;

    public String getTitle() {
        return this.movieNm;
    }

    public String getAudiCnt() {
        return this.audiCnt;
    }
}
