package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    public void testVerifyCustomer()    {
        Customer validCustomer = new Customer(
                "Chris Wolstenholme",
                "chris@muse.mu",
                "+44729388192"
        );
        Customer invalidCustomer = new Customer();

        Assertions.assertTrue(validCustomer.verifyCustomer());
        Assertions.assertFalse(invalidCustomer.verifyCustomer());
    }
}
