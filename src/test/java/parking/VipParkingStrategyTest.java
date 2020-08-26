package parking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {

    @Mock
    ParkingLot parkingLot;

    @Mock
    Car car;

    @Mock
    CarDao carDao;

    @InjectMocks
    VipParkingStrategy vipParkingStrategy;

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        //given
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(true).when(vipParkingStrategy).isAllowOverPark(any());
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(true);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = vipParkingStrategy.park(Collections.singletonList(new ParkingLot("Oliver", 10)), new Car("粤C123"));

        //then
        assertEquals("Oliver", receipt.getParkingLotName());
        assertEquals("粤C123", receipt.getCarName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        //given
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(any());
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn("Oliver");
        when(parkingLot.isFull()).thenReturn(true);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("粤C123");

        //when
        Receipt receipt = vipParkingStrategy.park(new ArrayList<ParkingLot>(), new Car("粤C123"));

        //then
        assertEquals("No Parking Lot", receipt.getParkingLotName());
        assertEquals("粤C123", receipt.getCarName());
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(carDao.isVip(any())).thenReturn(true);
        when(car.getName()).thenReturn("粤A123");

        //when
        Boolean receipt = vipParkingStrategy.isAllowOverPark(car);

        //then
        assertEquals(true, receipt);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(carDao.isVip(any())).thenReturn(true);
        when(car.getName()).thenReturn("粤C123");

        //when
        Boolean receipt = vipParkingStrategy.isAllowOverPark(car);

        //then
        assertEquals(false, receipt);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(carDao.isVip(any())).thenReturn(false);
        when(car.getName()).thenReturn("粤A123");

        //when
        Boolean receipt = vipParkingStrategy.isAllowOverPark(car);

        //then
        assertEquals(false, receipt);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(carDao.isVip(any())).thenReturn(true);
        when(car.getName()).thenReturn("粤C123");

        //when
        Boolean receipt = vipParkingStrategy.isAllowOverPark(car);

        //then
        assertEquals(false, receipt);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
