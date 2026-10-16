/*
 * MusicLibraryTest.java -- unit tests for the Lesson 13 exercise
 * ICS 4U0 - Lesson 13 Exercise: MusicLibrary
 *
 * These tests run MusicLibrary.main() exactly as it will be graded: they type
 * the input lines on System.in and check the lines it prints to System.out.
 * You don't need to change this file -- just run the tests (see README.md)
 * and fix MusicLibrary.java until they all pass.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MusicLibraryTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private Locale originalLocale;

    @BeforeEach
    public void redirectIoAndPinLocale() {
        originalIn = System.in;
        originalOut = System.out;
        originalLocale = Locale.getDefault();
        // printf follows the default locale -- this exercise doesn't print
        // decimals, but every test pins the locale before calling main() so
        // the harness behaves the same on every machine.
        Locale.setDefault(Locale.CANADA);
    }

    @AfterEach
    public void restoreIoAndLocale() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Locale.setDefault(originalLocale);
    }

    /**
     * Feeds {@code input} to MusicLibrary.main() on System.in and returns
     * everything it printed to System.out.
     */
    private String run(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));

        MusicLibrary.main(new String[0]);

        return captured.toString(StandardCharsets.UTF_8);
    }

    /**
     * Compares output line by line, trimming trailing whitespace on each
     * line and ignoring one blank line at the very end (println's final
     * newline). Everything else -- including every space, colon, and quote -- is
     * compared exactly.
     */
    private void assertOutputEquals(String expected, String actual) {
        String[] expectedLines = expected.stripTrailing().split("\n", -1);
        String[] actualLines = actual.stripTrailing().split("\n", -1);

        assertEquals(expectedLines.length, actualLines.length,
                "Expected " + expectedLines.length + " line(s) of output, got " + actualLines.length
                        + ".\n--- expected ---\n" + expected + "--- actual ---\n" + actual);

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].stripTrailing(), actualLines[i].stripTrailing(),
                    "Line " + (i + 1) + " didn't match.\n--- expected ---\n" + expected
                            + "--- actual ---\n" + actual);
        }
    }

    @Test
    public void exampleFromReadme() {
        String input = "4\nNorthern Lights\nThe Maples\n185\nPaper Boats\nJuniper Lane\n270\nGlass House\nThe Lanterns\n198\nDriftwood\nCedar & Stone\n215\n4\nNorthern Lights\nGlass House\nNorthern Lights\nOverpass\n";
        String expected = "No song called \"Overpass\"\n"
                + "Library:\n"
                + "1. Northern Lights by The Maples (3:05), plays: 2\n"
                + "2. Paper Boats by Juniper Lane (4:30), plays: 0\n"
                + "3. Glass House by The Lanterns (3:18), plays: 1\n"
                + "4. Driftwood by Cedar & Stone (3:35), plays: 0\n"
                + "Total length: 868 seconds\n"
                + "Most played: Northern Lights, plays: 2\n"
                + "Unplayed songs removed: 2\n"
                + "Songs left:\n"
                + "- Northern Lights\n"
                + "- Glass House\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void playFindsSongByTitle() {
        // Titles read with nextLine() are different String objects, so == never matches.
        // Compare titles with .equals(). Every request should play its song.
        String input = "3\nStatic\nLow Tide\n200\nEcho\nHarbour Lights\n140\nLighthouse\nNorth Shore\n181\n3\nEcho\nLighthouse\nEcho\n";
        String expected = "Library:\n"
                + "1. Static by Low Tide (3:20), plays: 0\n"
                + "2. Echo by Harbour Lights (2:20), plays: 2\n"
                + "3. Lighthouse by North Shore (3:01), plays: 1\n"
                + "Total length: 521 seconds\n"
                + "Most played: Echo, plays: 2\n"
                + "Unplayed songs removed: 1\n"
                + "Songs left:\n"
                + "- Echo\n"
                + "- Lighthouse\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void missingTitlePrintsMessageAndPlaysNothing() {
        // A title that isn't in the library prints one "No song called" line,
        // in quotes, and doesn't play any song. Titles must match exactly:
        // "intro" is not "Intro", so use equals, not equalsIgnoreCase.
        String input = "2\nIntro\nSilver Fern\n45\nInterlude\nSilver Fern\n59\n3\nOutro\nIntro\nintro\n";
        String expected = "No song called \"Outro\"\n"
                + "No song called \"intro\"\n"
                + "Library:\n"
                + "1. Intro by Silver Fern (0:45), plays: 1\n"
                + "2. Interlude by Silver Fern (0:59), plays: 0\n"
                + "Total length: 104 seconds\n"
                + "Most played: Intro, plays: 1\n"
                + "Unplayed songs removed: 1\n"
                + "Songs left:\n"
                + "- Intro\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void mostPlayedTieKeepsFirstSong() {
        // Two songs tie for most plays. Use > (not >=) so the first one in the
        // library stays the most played.
        String input = "3\nOverpass\nNine Rivers\n240\nSlow Burn\nAmber Coast\n300\nQuick One\nPaper Kites Club\n62\n4\nSlow Burn\nQuick One\nQuick One\nSlow Burn\n";
        String expected = "Library:\n"
                + "1. Overpass by Nine Rivers (4:00), plays: 0\n"
                + "2. Slow Burn by Amber Coast (5:00), plays: 2\n"
                + "3. Quick One by Paper Kites Club (1:02), plays: 2\n"
                + "Total length: 602 seconds\n"
                + "Most played: Slow Burn, plays: 2\n"
                + "Unplayed songs removed: 1\n"
                + "Songs left:\n"
                + "- Slow Burn\n"
                + "- Quick One\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void mostPlayedWhenNothingWasPlayed() {
        // No song is played, so the most played is the first song with 0 plays.
        // Start the search from library.get(0), not null. Every song is then removed.
        String input = "2\nStatic\nLow Tide\n200\nEcho\nHarbour Lights\n140\n0\n";
        String expected = "Library:\n"
                + "1. Static by Low Tide (3:20), plays: 0\n"
                + "2. Echo by Harbour Lights (2:20), plays: 0\n"
                + "Total length: 340 seconds\n"
                + "Most played: Static, plays: 0\n"
                + "Unplayed songs removed: 2\n"
                + "Songs left:\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void adjacentUnplayedSongsAreAllRemoved() {
        // Three unplayed songs sit next to each other. Removing while looping forward
        // skips the song that shifts into the removed spot. Loop backward (or i--).
        String input = "5\nLighthouse\nNorth Shore\n181\nDemo 4\nNorth Shore\n122\nDemo 5\nNorth Shore\n130\nDemo 6\nNorth Shore\n140\nHomecoming\nNorth Shore\n205\n2\nHomecoming\nLighthouse\n";
        String expected = "Library:\n"
                + "1. Lighthouse by North Shore (3:01), plays: 1\n"
                + "2. Demo 4 by North Shore (2:02), plays: 0\n"
                + "3. Demo 5 by North Shore (2:10), plays: 0\n"
                + "4. Demo 6 by North Shore (2:20), plays: 0\n"
                + "5. Homecoming by North Shore (3:25), plays: 1\n"
                + "Total length: 778 seconds\n"
                + "Most played: Lighthouse, plays: 1\n"
                + "Unplayed songs removed: 3\n"
                + "Songs left:\n"
                + "- Lighthouse\n"
                + "- Homecoming\n";
        assertOutputEquals(expected, run(input));
    }
}
