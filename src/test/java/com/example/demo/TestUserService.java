package com.example.demo;

import com.example.demo.entity.Address;
import com.example.demo.entity.Social;
import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.service.AddressService;
import com.example.demo.service.SocialService;
import com.example.demo.service.UserService;
import com.example.demo.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Order(1)
    @Test
    void testCreate() throws BaseException {
        String token = SecurityUtil.generateToken();

        User user = userService.saveUser(
                TestCreateData.email,
                TestCreateData.password,
                TestCreateData.name,
                token,
                new Date()
        );

        //Check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        //Check equals
        Assertions.assertEquals(TestCreateData.email, user.getEmail());
        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
        Assertions.assertTrue(isMatched);
        Assertions.assertEquals(TestCreateData.name, user.getName());
    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User updateUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updateUser);
        Assertions.assertEquals(TestUpdateData.name, updateUser.getName());
    }

    @Order(3)
    @Test
    void testCreateSocial() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.createSocial(user,
                SocialTestCreateData.facebook,
                SocialTestCreateData.Line,
                SocialTestCreateData.instagram,
                SocialTestCreateData.tiktok
        );

        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());
    }

    @Order(4)
    @Test
    void testCreateAddress() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());

        createAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        createAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);

    }

    private void createAddress(User user, String line1, String line2, String zipcode) {
        Address address = addressService.createAddress(
                user,
                line1,
                line2,
                zipcode
        );

        Assertions.assertNotNull(address);
        Assertions.assertEquals(line1, address.getLine1());
        Assertions.assertEquals(line2, address.getLine2());
        Assertions.assertEquals(zipcode, address.getZipcode());
    }

    @Order(4)
    @Test
    void testDelete() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        //Check Social
        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());

        //Check Address
        List<Address> addresses = user.getAddresses();
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertEquals(2,addresses.size() );

        userService.delete(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(optDelete.isEmpty());
    }

    interface TestCreateData {
        String email = "phetch@gmail.com";

        String password = "1234";

        String name = "phetch";
    }

    interface AddressTestCreateData {
        String line1 = "123/45";

        String line2 = "mueng";

        String zipcode = "37000";
    }

    interface AddressTestCreateData2 {
        String line1 = "456/78";

        String line2 = "mueng";

        String zipcode = "37001";
    }

    interface TestUpdateData {
        String name = "pirat";
    }

    interface SocialTestCreateData {
        String facebook = "Pirat Wannasiripipat";
        String Line = "";
        String instagram = "";
        String tiktok = "";
    }

}
