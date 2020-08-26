package parking;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

public class InOrderParkingStrategyTest {

    InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(false);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(new ParkingLot("Oliver", 10)), new Car("粤C123"));

        //then
        assertEquals("Oliver", receipt.getParkingLotName());
        assertEquals("粤C123", receipt.getCarName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(true);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = inOrderParkingStrategy.park(new ArrayList<ParkingLot>(), new Car("粤C123"));

        //then
        assertEquals("No Parking Lot", receipt.getParkingLotName());
        assertEquals("粤C123", receipt.getCarName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        //given
        InOrderParkingStrategy inOrderParkingStrategy = mock(InOrderParkingStrategy.class);
        when(inOrderParkingStrategy.park(any(), any())).thenCallRealMethod();
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(true);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = inOrderParkingStrategy.park(new ArrayList<ParkingLot>(), new Car("粤C123"));

        //then
        verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(any());
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        //given
        InOrderParkingStrategy inOrderParkingStrategy = mock(InOrderParkingStrategy.class);
        when(inOrderParkingStrategy.park(any(), any())).thenCallRealMethod();
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(false);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(new ParkingLot("Oliver", 10)), new Car("粤C123"));

        //then
        verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        //given
        InOrderParkingStrategy inOrderParkingStrategy = mock(InOrderParkingStrategy.class);
        when(inOrderParkingStrategy.park(any(), any())).thenCallRealMethod();
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(true);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = inOrderParkingStrategy.park(Collections.singletonList(new ParkingLot("Oliver", 10)), new Car("粤C123"));

        //then
        verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        //given
        InOrderParkingStrategy inOrderParkingStrategy = mock(InOrderParkingStrategy.class);
        when(inOrderParkingStrategy.park(any(), any())).thenCallRealMethod();
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");
        ParkingLot parkingLot1 = new ParkingLot("Oliver", 0);
        ParkingLot parkingLot2 = new ParkingLot("Eric", 10);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);

        //when
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, new Car("粤C123"));

        //then
        verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
    }


}
