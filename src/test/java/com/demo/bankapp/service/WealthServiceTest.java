package com.demo.bankapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.repository.WealthRepository;
import com.demo.bankapp.service.concretions.WealthService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class WealthServiceTest {

    @Mock
    private WealthRepository repository;

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

    mockedWealth = new Wealth(5125L, mockedWealthMap);

    when(repository.findById(anyLong()))
            .thenReturn(Optional.of(mockedWealth));

    service = spy(new WealthService(repository));

    Map<String, Double> fakeRates = new HashMap<>();
    fakeRates.put("USD", 1.0);
    fakeRates.put("EUR", 1.0);
    fakeRates.put("TRY", 1.0);
    fakeRates.put("AUD", 1.0);

    doReturn(fakeRates)
            .when(service)
            .getCurrencyRates();
}
    @Test
    public void newWealthRecord() {
        service.newWealthRecord(25161L);
    }

    @Test
    public void makeWealthExchange() {
        service.makeWealthExchange(mockedUserId, "USD", BigDecimal.valueOf(150), true);
    }
}