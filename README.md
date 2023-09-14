# Generics Demo
To showcase the use of generics in Java.

## Content
- This example builds on the SP1 group project on 3rd semester.
- `A Generic Interface (IDAO) to support the general functionality of a DAO.
- An abstract class to implement the generic interface with generic methods (ADAO).
- A concrete class to implement the abstract class, so it can be instantiated (DAO and PersonDAO).

## Challenges
- The abstract class ADAO has functionality like getAll and getById, which must have a ClassType to communitcate to JPQL.
  - This means that when constructing a DAO (extending the ADAO) the ClassType must be passed to the constructor.
  - The PersonDAO has extra functionality like getPersonByPhone etc. Which is why it extends ADAO and adds the extra functionality.
- All JPA Entities implements the IJPAEntity interface, which has a getId() method.
  - This means that the ADAO can use the getId() method to get the id of the entity, without knowing the type of the entity.
  - This is used in the getById() method in the ADAO.
