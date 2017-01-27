package com.makeupdepot.inventory.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by chassiness on 12/7/16.
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductRepositoryWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductPrices() throws Exception {
//        mockMvc.perform(
//                get("/employees")
//        )
//               .andExpect(status().isOk())
//               .andExpect(content().contentType("application/hal+json"));
    }

    @Test
    public void findByProductBrand() throws Exception {

    }

    @Test
    public void findByProductType() throws Exception {

    }

    @Test
    public void findByProductRetailer() throws Exception {

    }

    @Test
    public void findByProductUnitCost() throws Exception {

    }

}