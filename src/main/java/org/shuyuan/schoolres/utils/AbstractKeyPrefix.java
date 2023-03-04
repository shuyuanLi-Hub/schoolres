package org.shuyuan.schoolres.utils;

public abstract class AbstractKeyPrefix implements KeyPrefix
{
    private final int expireSeconds;
    private final String prefix;

    public AbstractKeyPrefix(String prefix)
    {
        this(-1, prefix);
    }

    public AbstractKeyPrefix(int expireSeconds, String prefix)
    {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds()
    {
        return expireSeconds;
    }

    @Override
    public String getPrefix()
    {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
