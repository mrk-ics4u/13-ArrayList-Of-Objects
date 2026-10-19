# Lesson 13 - ArrayList of Objects

In Lesson 8 an `ArrayList` held `String`s and `Integer`s. Those are objects too, so an `ArrayList` can hold objects of any class, including ones you write. A game keeps a list of `Enemy` objects, a music app keeps a list of `Song` objects. Your project should include hundreds of pieces of data, which means you may want hundreds of objects!

This lesson uses the `Enemy` class from Lesson 11.

---

## 1. Creating the List

The type parameter is the class name:

```java
import java.util.ArrayList;

ArrayList<Enemy> enemies = new ArrayList<Enemy>();
enemies.add(new Enemy("Goblin", 30, 4));
enemies.add(new Enemy("Orc", 50, 8));

Enemy boss = new Enemy("Dragon", 200, 25);
enemies.add(boss);
```

`new Enemy(...)` creates the object and `add` puts it in the list, in one line. You don't need a variable for every object. That's the point of the list.

Every method from Lesson 8 works the same way: `size()`, `get(i)`, `set(i, x)`, `add(i, x)`, `remove(i)`.

Printing the list prints `[Enemy@7ea987ac, Enemy@12a3a380, ...]`, because `Enemy` has no `toString` (Lesson 12). Print the attributes with getters instead.

---

## 2. Calling Methods on an Element

`enemies.get(0)` **is** an `Enemy`, so you can put a dot after it:

```java
System.out.println(enemies.get(0).getName());   // Goblin
enemies.get(1).takeDamage(20);
```

Java does the `get(0)` first, then calls `getName()` on the object it returned. When you use the same element more than once, store it in a variable first. It's easier to read:

```java
Enemy orc = enemies.get(1);
orc.takeDamage(20);
System.out.println(orc.getName() + ": " + orc.getHealth());
```

---

## 3. The List Holds References

An `ArrayList` doesn't hold the objects themselves. It holds **references** to them, the same arrows from Lesson 10. `get` hands you a copy of the arrow, not a copy of the object:

```java
Enemy orc = enemies.get(1);
orc.takeDamage(20);
System.out.println(enemies.get(1).getHealth());   // 30, not 50
```

`orc` and `enemies.get(1)` point to the **same** `Enemy`, so the change shows up in the list. The same is true for `boss`: it was added to the list, so `boss.takeDamage(50)` changes the dragon in the list.

This is different from Lesson 8. `Integer` objects can't be changed, so you had to `set` a new value. An `Enemy` can be changed through any reference to it, so there's no `set` needed.

---

## 4. Traversing

Both loops work. The enhanced for gives you each object in turn:

```java
for (Enemy e : enemies) {
    e.takeDamage(10);                          // every enemy loses 10
}

for (int i = 0; i < enemies.size(); i++) {
    Enemy e = enemies.get(i);
    System.out.println(i + ": " + e.getName() + " (" + e.getHealth() + " hp)");
}
```

In Lesson 8, changing `s` in `for (int s : scores)` didn't change the list. Here, `e.takeDamage(10)` does change the list, because `e` is a reference to the object that's in it. Calling methods on `e` is fine. Adding or removing inside an enhanced for loop is still a `ConcurrentModificationException`.

Use the enhanced for when you need every object and don't need the index. Use the index loop when you need `i` (numbering, removing, comparing neighbours).

---

## 5. Algorithms with Getters

The algorithms from Lessons 7 and 8 don't change. The only difference is that you call a getter to get the value you're working with.

**Sum:**

```java
int totalDamage = 0;
for (Enemy e : enemies) {
    totalDamage = totalDamage + e.getDamage();
}
```

**Max:** keep the whole **object**, not just the number. Then you have its name and everything else too.

```java
Enemy strongest = enemies.get(0);
for (Enemy e : enemies) {
    if (e.getDamage() > strongest.getDamage()) {
        strongest = e;
    }
}
System.out.println(strongest.getName());
```

**Count:**

```java
int defeated = 0;
for (Enemy e : enemies) {
    if (e.isDefeated()) {
        defeated++;
    }
}
```

**Find:** start with `null` (no object yet), and compare `String`s with `.equals`:

```java
Enemy found = null;
for (Enemy e : enemies) {
    if (e.getName().equals("Troll")) {
        found = e;
    }
}
if (found != null) {
    found.takeDamage(100);
}
```

Check `found != null` before calling a method on it. Calling a method on `null` throws `NullPointerException`.

**Remove:** the same backward loop from Lesson 8:

```java
for (int i = enemies.size() - 1; i >= 0; i--) {
    if (enemies.get(i).isDefeated()) {
        enemies.remove(i);
    }
}
```

`remove(i)` returns the object it removed, so `Enemy gone = enemies.remove(i);` lets you still use it.

> **AP students:** the third free-response question on the exam (Data Analysis with ArrayList) is almost always this lesson: a class you're given, an `ArrayList` of its objects, and one method to write that traverses it using getters, often removing elements. Practise writing these as `static` methods that take the list as a parameter.

---

## 6. Common Bugs

**Forgetting the parentheses on a getter.** `enemies.get(i).getName` is a compile error. It's `getName()`.

**`==` on Strings.** `e.getName() == target` compares references, not text, and is usually `false` for text you read with `nextLine()`. Use `.equals`.

**Starting a max at `null` or `0`.** `strongest.getDamage()` on `null` throws `NullPointerException`. Start with `enemies.get(0)`.

**Declaring the list without creating it.** `ArrayList<Enemy> enemies;` then `enemies.add(...)` is a compile error ("might not have been initialized"). It needs `= new ArrayList<Enemy>();`.

**Removing forward without adjusting `i`.** Adjacent matches get skipped. Loop backward.

**Adding or removing in an enhanced for.** `ConcurrentModificationException`.

---

## Try It Yourself

Run the companion file from the `src` folder:

```bash
javac ArrayListOfObjectsDemo.java
java ArrayListOfObjectsDemo
```

`ArrayListOfObjectsDemo` uses the `Enemy` class in `Enemy.java`.

Then work on the exercise in `MusicLibrary.java`. It uses the `Song` class you wrote in Lesson 11, already complete in `Song.java`.

### AP Extension

1. What does this print? Explain why.

```java
ArrayList<Enemy> list = new ArrayList<Enemy>();
Enemy a = new Enemy("Bat", 20, 2);
list.add(a);
list.add(a);
list.get(0).takeDamage(5);
System.out.println(list.get(1).getHealth());
```

2. What does this print? Explain why.

```java
Enemy first = list.get(0);
list.set(0, new Enemy("Wolf", 40, 6));
System.out.println(first.getName() + " " + list.get(0).getName());
```

3. Write `public static int removeWeak(ArrayList<Enemy> list, int minDamage)`. It removes every enemy whose damage is less than `minDamage` and returns how many were removed.

---

## Course Expectations

### ICS 4U

"I can use one-dimensional arrays of compound data types (i.e. objects)" is a requirement of the course and will be on your programming test and expected in your programming project.

### AP Expectations

The following are expectations of the AP exam and will show up on the final exam:

- An `ArrayList` is mutable in size and contains object references (Topic 4.8, 4.8.A.1)
- `ArrayList<E>` specifies the element type; `get`, `set`, and `remove` return type `E`, so a method can be called directly on the result (4.8.A.3, 4.8.A.5)
- When a method returns an object reference, the reference is returned, not a copy of the object; changing the object through that reference changes the object in the list (3.6.A.2)
- When an object reference is passed as an argument, the parameter gets a copy of the reference, not a new copy of the object, so the method can change the object (3.6.A.1)
- Traversing an `ArrayList` of objects with an index loop or enhanced for loop; don't add or remove in an enhanced for loop (Topic 4.9, 4.9.A.1, 4.9.A.4)
- Deleting during a traversal needs a special technique to avoid skipping elements (4.9.A.2)
- Standard algorithms on `ArrayList` objects: min/max, sum/average, at least one, all, count, and deleting elements (Topic 4.10, 4.10.A.1)
- Calling a method on `null` throws a `NullPointerException` (Topic 1.14, 1.14.A.2)
