# 💾 Persistence

A class in Java to introduce the data persistence.

## 💪 Motivation

During the OOP lesson a class was delivered for the same purpose as this one, but it contained a lot of unnecessary complexity. Realizing my difficulty and the difficulty of team using it, I asked the teacher permission to create one. And here it is!

## ⚙️ Operation

The data is recorded in CSV standard.

Representation:

- One table per CSV file.

- One registry per line.

- One attribute by value in CSV.

<br/>

## How to use?

### Example: CRUD operations in user table

```java
import example.Persistence;
import java.util.ArrayList;
import java.util.Arrays;

public class PersistenceExample {
    public static void main(String[] args) {
        // Builder initialization with path to file
        Persistence users = new Persistence("src/db/users.csv");

        // Load file data to temporary database
        users.load();

        // Create a new user
        users.add(
            new ArrayList<>(Arrays.asList("John Doe", "1992-11-09"))
        );

        // Read user data
        System.out.println(users.get(0));

        // Update user data
        users.update(0, new ArrayList<>(Arrays.asList("Jane Doe", "1993-03-18")));

        // Remove user
        users.remove(0);

        // Persist changes in the temporary database
        users.save();
    }
}
```
