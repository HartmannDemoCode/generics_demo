package dk.cphbusiness.dat.persistence.dao;

import dk.cphbusiness.dat.config.HibernateConfig;
import dk.cphbusiness.dat.persistence.dao.PersonDAO;
import dk.cphbusiness.dat.persistence.dao.DAO;
import dk.cphbusiness.dat.model.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaoTest {

    private final PersonDAO personDAO = PersonDAO.getPersonDao();
    private final DAO<Address> addressDAO = new DAO<>(Address.class);
    private final DAO<Zip> zipDAO = new DAO<>(Zip.class);
    // Create new person
    Person person1, person2, person3;
    Zip zip1, zip2, zip3;
    Address address;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

        // Use the test database
        personDAO.setEntityManagerFactory(emf);
        personDAO.truncate();
        zipDAO.setEntityManagerFactory(emf);
        zipDAO.truncate();
        addressDAO.setEntityManagerFactory(emf);
        addressDAO.truncate();

        person1 = createPerson(1);
        person2 = createPerson(2);
        person3 = createPerson(3);
        person1 = personDAO.save(person1);
        person2 = personDAO.save(person2);
        person3 = personDAO.save(person3);

        zip1 = new Zip(2760, "Måløv");
        zip2 = new Zip(3050, "Humlebæk");
        zip3 = new Zip(2800, "Lyngby");
        zipDAO.save(zip1);
        zipDAO.save(zip2);
        zipDAO.save(zip3);

        address = new Address("Rolighedsvej 333", zip1);
        addressDAO.save(address);
    }

    @Test
    void testGetPersonById() {
        System.out.println("test Get Person By Id");

        Person expected = person1;
        Person actual = personDAO.findById(person1.getId());

        assertEquals(expected, actual); // equals method looks at id, email, created date
    }

    @Test
    void testUpdatePerson() {
        System.out.println("testUpdatePerson");
        person1.setFirstName("Test2");
        personDAO.update(person1);
        Person personFromDB = personDAO.findById( person1.getId());

        assertEquals(person1.getFirstName(), personFromDB.getFirstName());
    }

    @Test
    void testDeletePerson() {
        System.out.println("testDeletePerson");
        personDAO.delete(person1);
        int expected = 2;
        int actual = personDAO.getAll().size();

        assertEquals(expected, actual);
    }


    @Test
    void testGetAllPeopleByZip() {
        System.out.println("testGetAllByPeopleZip");
        person1.setAddress(address);
        person2.setAddress(address);
        personDAO.update(person1);
        personDAO.update(person2);

        int expected = 2;
        int actual = personDAO.getAllByZip(zip1.getZip()).size();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllZip() {
        System.out.println("test Get All Zip Codes");
        List<Zip> zips = zipDAO.getAll();

        assertEquals(3, zips.size());
    }

    @Test
    void testGetPersonByMobile() {
        System.out.println("testGetPersonByMobile");
        person1.addPhone(new Phone("90909090", "Mobile"));
        person1.addPhone(new Phone("32323232", "Mobile"));
        personDAO.update(person1);
        personDAO.update(person2);

        Person person = personDAO.getByPhone("90909090");
        System.out.println(person.getPhones());

        assertEquals(person1.getId(), person.getId());
    }

    @Test
    void testGetPersonByEmail() {
        System.out.println("testGetPersonByEmail");
        person1.setEmail("a@a.dk");
        personDAO.update(person1);

        Person person = personDAO.getPersonByEmail("a@a.dk");

        assertEquals(person1, person);
    }

    protected Person createPerson(int uniqueId) {
        Person person = new Person();
        person.setFirstName("Test" + uniqueId);
        person.setLastName("Test" + uniqueId);
        person.setEmail("test" + uniqueId + "@test.dk");
        person.setBirthDate(LocalDate.of(2000, 1, 1));
        return person;
    }

    protected Hobby createHobby(String name) {
        return Hobby.builder()
                .name(name)
                .category(HobbyCategory.GENERAL)
                .build();
    }
}