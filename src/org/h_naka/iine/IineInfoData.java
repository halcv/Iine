package org.h_naka.iine;

public class IineInfoData {
	private int m_likeCount;
    private int m_commentCount;
    private int m_shareCount;

    public IineInfoData() {
        m_likeCount    = 0;
        m_commentCount = 0;
        m_shareCount   = 0;
    }

    public void setLike(int like) {
        m_likeCount = like;
    }

    public int getLike() {
        return m_likeCount;
    }

    public void setComment(int comment) {
        m_commentCount = comment;
    }

    public int getComment() {
        return m_commentCount;
    }
    public void setShare(int share) {
        m_shareCount = share;
    }

    public int getShare() {
        return m_shareCount;
    }
}
