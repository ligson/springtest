package springtest.util;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by trq on 2016/8/26.
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return Md5Crypt.md5Crypt(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Md5Crypt.md5Crypt(rawPassword.toString().getBytes()).equals(encodedPassword);
    }
}
