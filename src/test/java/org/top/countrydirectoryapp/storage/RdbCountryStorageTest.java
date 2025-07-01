package org.top.countrydirectoryapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.top.countrydirectoryapp.model.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RdbCountryStorageTest {

    private RdbCountryStorage storageMock;
    private CountryScenario scenario;

    @BeforeEach
    public void setUp() {
        storageMock = Mockito.mock(RdbCountryStorage.class, invocation -> {
            throw new UnsupportedOperationException(invocation.getMethod().getName());
        });
        scenario = new CountryScenario(storageMock);

    }

    @Nested
    public class ListAllTest {
        @Test
        public void success() {
            //List<CountryDbEntity> expected = List.of(new CountryDbEntity(new Country()));
            List<Country> expected = List.of(new Country());
            Mockito.doReturn(expected).when(storageMock).getAll();

            List<Country> actual = scenario.listAll();
            assertEquals(expected, actual);
        }

        @Test
        void returnAllCountriesTest() {
//            List<CountryDbEntity> expected = List.of(new CountryDbEntity(
//                    new Country("Canada", "Canada", "CA", "CAN", "124", 38005238L, 9984670L)
//            ));
            List<Country> expected = List.of(
                    new Country("Canada", "Canada", "CA", "CAN", "124", 38005238L, 9984670L)
            );
            Mockito.doReturn(expected).when(storageMock).getAll();

            List<Country> actual = scenario.listAll();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class GetByCodeTest {
        @Test
        public void returnCodeIsoAloha3Test() {
            String originCode = "JPN";
            Country expected = new Country("", "", "", originCode, "", 1L, 1L);
            Mockito.doReturn(Optional.of(expected))
                    .when(storageMock)
                    .get(originCode);
            Country actual = scenario.get(originCode);
            assertEquals(expected, actual);
        }

        @Test
        public void returnCodeIsoAloha2Test() {
            String originCode = "JP";
            Country expected = new Country("", "", originCode, "", "", 1L, 1L);
            Mockito.doReturn(Optional.of(expected))
                    .when(storageMock)
                    .get(originCode);
            Country actual = scenario.get(originCode);
            assertEquals(expected, actual);
        }

        @Test
        public void returnCodeIsoNumericTest() {
            String originCode = "123";
            Country expected = new Country("", "", "", "", originCode, 1L, 1L);
            Mockito.doReturn(Optional.of(expected))
                    .when(storageMock)
                    .get(originCode);
            Country actual = scenario.get(originCode);
            assertEquals(expected, actual);
        }


        @Test
        public void countryNotFound() {
            String originCode = "TST";
            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(originCode);
            assertThrows(
                    CountryNotFoundException.class, () -> scenario.get(originCode)
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "C", "INVALID"})
        public void invalidCode(String origin) {
            assertThrows(CountryNotFoundException.class, () -> scenario.get(origin));
        }

        @Test
        public void nullCode() {
            assertThrows(NullPointerException.class, () -> scenario.get(null));
        }
    }

    @Nested
    public class StoreTest {
        @Test
        public void success() {

            Country origin = new Country("", "", "JP", "", "", 1L, 1L);
            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(origin.getIsoAlpha2());
            // конфигурация Mock
            Mockito.doNothing().when(storageMock).store(origin);
            scenario.store(origin);
            // проверим факт того, что сценарии вызвал метод store Store
            Mockito.verify(storageMock).store(origin);
        }

        @Test
        public void duplicatedCode() {
            Country origin = new Country("", "", "JP", "", "", 1L, 1L);
            Mockito.doReturn(Optional.of(origin))
                    .when(storageMock)
                    .get(origin.getIsoAlpha2());
            assertThrows(DuplicatedCodeException.class, () -> scenario.store(origin));
        }
    }

    @Nested
    public class EditTest {
        @Test
        public void success() {
            Country origin = new Country("", "", "JP", "", "", 1L, 1L);
            Mockito.doReturn(Optional.of(origin)).when(storageMock)
                    .get(origin.isValid());
            Mockito.doNothing().when(storageMock).edit(origin);
            scenario.edit(origin);
            Mockito.verify(storageMock).edit(origin);
        }

        @Test
        public void countryNotFound() {
            Country origin = new Country("", "", "JPG", "", "", 1L, 1L);
            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(origin.isValid());
            assertThrows(
                    InvalidCodeException.class, () -> scenario.edit(origin)
            );
        }
    }

    @Nested
    public class DeleteTest {
        @Test
        public void success() {
            String origin = "TS";
            Mockito.doReturn(Optional.of(origin))
                    .when(storageMock).get(origin);
            Mockito.doNothing().when(storageMock).delete(origin);
            scenario.delete(origin);
            Mockito.verify(storageMock).delete(origin);
        }

        @Test
        public void countryNotFound() {
            String origin = "TS";
            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(origin);
            assertThrows(
                    CountryNotFoundException.class, () -> scenario.delete(origin)
            );
        }
    }
}