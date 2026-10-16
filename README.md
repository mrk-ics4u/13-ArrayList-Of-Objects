# ICS 4U0 — Lesson 13: ArrayList of Objects

## Exercise

You're building the library screen of the music player app. It stores every song in an `ArrayList<Song>`, plays songs by title, and reports on the library.

The `Song` class from Lesson 11 is already complete in `src/Song.java`. **Don't change it.** Write your program in the `main` method of `src/MusicLibrary.java`. The numbered comments there are the six steps, in order.

```
┌─────────────────────────────────────┐
│ Song                                │
├─────────────────────────────────────┤
│ - title : String                    │
│ - artist : String                   │
│ - lengthSeconds : int               │
│ - plays : int                       │
├─────────────────────────────────────┤
│ + Song(title : String,              │
│        artist : String,             │
│        lengthSeconds : int)         │
│ + getTitle() : String               │
│ + getArtist() : String              │
│ + getLengthSeconds() : int          │
│ + getPlays() : int                  │
│ + setTitle(title : String) : void   │
│ + play() : void                     │
│ + getLengthText() : String          │
└─────────────────────────────────────┘
```

Everything you need is from Lesson 13:

- an **`ArrayList<Song>`** filled with `new Song(...)` objects,
- **calling methods** on the elements (`play()`, the getters),
- **find**, **sum**, and **max** traversals that use getters, and
- **removing** while traversing, without skipping any songs.

Your program must:

1. Read the songs and add a new `Song` for each one to the list.
2. Read each play request. If a song has exactly that title (capitals matter), call `play()` on it. If no song has that title, print `No song called "<title>"` and play nothing. Titles are never repeated in the library.
3. Print `Library:`, then one numbered line per song, in the order they were added.
4. Print the total length of all songs, in seconds.
5. Print the song with the most plays. If two songs tie, print the one that comes **first** in the library.
6. Remove every song with 0 plays. Print how many were removed, then `Songs left:` and the title of each song still in the list.

---

## Input

| Line | Value | Type |
|------|-------|------|
| 1 | Number of songs, `n` (at least 1) | whole number |
| next 3 × `n` | Each song's title, artist, and length in seconds, one per line | text, text, whole number |
| next | Number of play requests, `p` (can be 0) | whole number |
| next `p` | One title per line to play | text |

Example input:

```
4
Northern Lights
The Maples
185
Paper Boats
Juniper Lane
270
Glass House
The Lanterns
198
Driftwood
Cedar & Stone
215
4
Northern Lights
Glass House
Northern Lights
Overpass
```

---

## Output

```
No song called "<title>"                              (one line per title not found, while reading the requests)
Library:
<number>. <title> by <artist> (<length text>), plays: <plays>
Total length: <total> seconds
Most played: <title>, plays: <plays>
Unplayed songs removed: <count>
Songs left:
- <title>
```

For the example input above, the program must print **exactly**:

```
No song called "Overpass"
Library:
1. Northern Lights by The Maples (3:05), plays: 2
2. Paper Boats by Juniper Lane (4:30), plays: 0
3. Glass House by The Lanterns (3:18), plays: 1
4. Driftwood by Cedar & Stone (3:35), plays: 0
Total length: 868 seconds
Most played: Northern Lights, plays: 2
Unplayed songs removed: 2
Songs left:
- Northern Lights
- Glass House
```

Your output must match exactly, including spaces, colons, quotes, and the dash before each title left.

---

## Testing

- Test your code yourself first.
- Open the **Testing** panel from the sidebar (flask icon) and click ▶ **Run Tests**.

A green check next to a test means it passed; a red X means it failed and will show you the expected vs. actual output.
