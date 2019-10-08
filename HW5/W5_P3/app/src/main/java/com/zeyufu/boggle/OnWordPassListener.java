package com.zeyufu.boggle;

public interface OnWordPassListener {
    /**
     * Pass word from WordFragment to ScoreFragment
     * @param word the word to pass
     */
    public void passWord(String word);
}
