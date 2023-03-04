package org.shuyuan.schoolres.domain.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash("user")
@Data
public class LoginU
{
    @Id
    private Integer id;
    @Indexed
    private String account;
    @Indexed
    private String passwd;

    @TimeToLive(unit = TimeUnit.HOURS)
    private Long ttl;

    public LoginU(String account, String passwd, Long ttl)
    {
        this.account = account;
        this.passwd = passwd;
        this.ttl = ttl;
    }
}
