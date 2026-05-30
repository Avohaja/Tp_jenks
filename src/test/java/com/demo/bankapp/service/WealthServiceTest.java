package com.demo.bankapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.InsufficientFundsException;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.repository.WealthRepository;
import com.demo.bankapp.service.concretions.WealthService;

@RunWith(MockitoJUnitRunner.class)
public class WealthServiceTest {

    @Mock
    private WealthRepository repository;

    @InjectMocks
    private WealthService service;

    private Wealth mockedWealth;
    private Long mockedUserId;

    @Before
    public void setUp() {

        Map<String, BigDecimal> mockedWealthMap = new HashMap<>();
        mockedWealthMap.put("USD", BigDecimal.valueOf(2500));
        mockedWealthMap.put("TRY", BigDecimal.valueOf(2000));
        mockedWealthMap.put("EUR", BigDecimal.valueOf(3000));
        mockedWealthMap.put("AUD", BigDecimal.ZERO);

        mockedUserId = 5125L;
        mockedWealth = new Wealth(mockedUserId, mockedWealthMap);

        Mockito.when(repository.findById(mockedUserId))
               .thenReturn(Optional.of(mockedWealth));

        // 🔥 FIX CRUCIAL
        WealthService spyService = Mockito.spy(service);

        Map<String, Double> fakeRates = new HashMap<>();
        fakeRates.put("USD", 1.0);
        fakeRates.put("EUR", 1.0);
        fakeRates.put("TRY", 1.0);
        fakeRates.put("AUD", 1.0);

        Mockito.doReturn(fakeRates)
               .when(spyService)
               .getCurrencyRates();

        service = spyService;
    }
}