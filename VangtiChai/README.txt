VangtiChai v2 — Android Studio project
Open this folder in Android Studio and run after Gradle sync.

Includes a forest-green fintech UI, formatted amount card, total notes/coins badge,
scrollable denomination list with counts/subtotals, and custom keypad with ripple
feedback, Clear and Backspace.

Rotation fix: the manifest intentionally does NOT declare android:configChanges.
Android therefore recreates MainActivity on rotation. onSaveInstanceState saves
the amount string; onCreate restores it; render() recalculates the breakdown from
that amount. Do not add configChanges for orientation or screenSize.

Calculation uses integer division and remainder for denominations 500,100,50,20,10,5,2,1.
Tests: 234 => 100x2,20x1,10x1,2x2 (6 total);
786 => 500x1,100x2,50x1,20x1,10x1,5x1,1x1 (8 total);
0 => all counts zero.

Input is limited to 12 digits. Test Pixel XL and Nexus 10 in portrait and landscape.
Build/emulator execution has not been independently verified in this package.
