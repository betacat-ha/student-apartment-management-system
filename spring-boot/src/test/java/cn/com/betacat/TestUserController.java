package cn.com.betacat;

import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.entity.Bill;
import cn.com.betacat.entity.Usage;
import cn.com.betacat.entity.User;
import cn.com.betacat.services.BillService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestUserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private BillService billService;

    @Test
    public void testGetUserList() {
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void testGetUsageByBuildingId() {
        List<Usage> usageList = usageMapper.selectAllUsages();
        for (Usage usage : usageList) {
            System.out.println("usage = " + usage);
        }
    }

    @Test
    public void genJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","Tom");

        String jwt = Jwts.builder()
                .setClaims(claims) //自定义内容(载荷)
                .signWith(SignatureAlgorithm.HS256, "betacat") //签名算法
                .setExpiration(new Date(System.currentTimeMillis() + 60*1000)) //有效期
                .compact();

        System.out.println(jwt);
    }

    @Test
    public void parseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("betacat")//指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNjg2ODc2ODQxLCJ1c2VybmFtZSI6IlRvbSJ9.3EM4uKLBH0rYFQP2YCg7Ko5lvNUSWPDXs3S3dxQjeSM")
                .getBody();

        System.out.println(claims);
    }

    @Test
    public void testGenerateBill() {
        billService.generateBill(11);
    }


}
