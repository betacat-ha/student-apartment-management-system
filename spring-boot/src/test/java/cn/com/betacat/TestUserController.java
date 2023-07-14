/*
 * Copyright 2023 BetaCat_HA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.betacat;

import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.services.BillService;
import cn.com.betacat.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private BillService billService;

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private StudentMapper studentMapper;

    //@Test
    //public void testGetUserList() {
    //    List<User> userList = userMapper.selectList(null);
    //    for (User user : userList) {
    //        System.out.println("user = " + user);
    //    }
    //}
    //
    //@Test
    //public void testGetUsageByBuildingId() {
    //    List<Usage> usageList = usageMapper.selectAllUsages();
    //    for (Usage usage : usageList) {
    //        System.out.println("usage = " + usage);
    //    }
    //}
    //
    //@Test
    //public void genJwt(){
    //    Map<String,Object> claims = new HashMap<>();
    //    claims.put("id",1);
    //    claims.put("username","Tom");
    //
    //    String jwt = Jwts.builder()
    //            .setClaims(claims) //自定义内容(载荷)
    //            .signWith(SignatureAlgorithm.HS256, "betacat") //签名算法
    //            .setExpiration(new Date(System.currentTimeMillis() + 60*1000)) //有效期
    //            .compact();
    //
    //    System.out.println(jwt);
    //}
    //
    //@Test
    //public void parseJwt(){
    //    Claims claims = Jwts.parser()
    //            .setSigningKey("betacat")//指定签名密钥（必须保证和生成令牌时使用相同的签名密钥）
    //            .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNjg2ODc2ODQxLCJ1c2VybmFtZSI6IlRvbSJ9.3EM4uKLBH0rYFQP2YCg7Ko5lvNUSWPDXs3S3dxQjeSM")
    //            .getBody();
    //
    //    System.out.println(claims);
    //}
    //
    //@Test
    //public void testGenerateBill() {
    //    billService.generateBill(11);
    //}
    //
    //@Test
    //public void testWriteExcel() {
    //    //List<Student> studentList = studentMapper.selectList(null);
    //    //String filePath = excelUtil.write("学生数据", studentList, Student.class);
    //    //System.out.println("filePath = " + filePath);
    //}


}
