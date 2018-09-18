package com.mistra.base.lombok;

/**
 * @Author: WangRui
 * @Date: 2018-09-18
 * Time: 下午2:07
 * Description:
 */
public class LombokTest {
    public static void main(String[] args) {
        LombokEntity lombokEntity1 = new LombokEntity();
        lombokEntity1.setId(1L);
        lombokEntity1.setUserName("Mistra");
        lombokEntity1.setEmail("wrmistra@gamil.com");
        lombokEntity1.setPassword("123456");
        lombokEntity1.setNotExistParam("NotExistParam");

        System.out.println("lombokEntity1------->"+lombokEntity1.toString());
        System.out.println("lombokEntity1------->"+lombokEntity1.hashCode());

        LombokEntity lombokEntity2 = new LombokEntity(2L,"Mistra2","xxxx@mail","111","YYY");
        System.out.println("lombokEntity2------->"+lombokEntity2.toString());
        System.out.println("lombokEntity2------->"+lombokEntity2.hashCode());
    }

}
