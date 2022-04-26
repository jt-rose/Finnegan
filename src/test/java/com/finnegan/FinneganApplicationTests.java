package com.finnegan;

import com.finnegan.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FinneganApplicationTests {
    @Autowired
    private UserController controller; // add transaction later

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}


//
//
//package com.finnegan;
//
//        import static org.assertj.core.api.Assertions.assertThat;
//
//        import org.junit.Test;
//        import org.junit.runner.RunWith;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.context.SpringBootTest;
//        import org.springframework.test.context.junit4.SpringRunner;
//
//        import com.finnegan.web.OwnerController;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class FinneganApplicationTests {
//    @Autowired
//    private OwnerController controller; // add transaction later
//    // rewrite to just users later
//
//    @Test
//    public void contextLoads() {
//        assertThat(controller).isNotNull();
//    }
//
//}