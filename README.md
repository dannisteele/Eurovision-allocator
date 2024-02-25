# Eurovision-allocator
Allocates out all Eurovision entries for a group of people to "support" while watching the show.

Running the Eurovision.java through terminal should (hopefully!) be fairly straightforward. Scroll to the bottom for a guide to doing this if needed.

Once ran, you will be prompted to enter the amount of players.

Next, it will ask if there is a set list you wish to load from the Set_Lists folder. Presently, there is only the data from 2022 and 2023 but I will add more over time, and will add 2024 once all participants and songs have been announced. 

If using this for something other that Eurovision, such as for a general sweepstake, simply don't load one and input details for whatever it is you are doing instead (it will still reference songs and countries in the prompts, but does not pull this through to the end details).

Once finished, you can save it if you wish to use this same information again at a later date, or otherwise can just run it without saving anything.

**WARNING**: This does not save the actual outcome anywhere, so if this is for a sweepstake then make sure that you copy it somewhere. I'll probably add this as a feature at a later date.

Have fun!

**The newbie guide**
- Click the green code button, and click download zip.
- If on Windows, unzip this (not necessary on Mac).
- Right click the folder (by default it is named Eurovision-allocator-main) and open in terminal or command line.
- Copy in the following:
  ```
  javac Eurovision.java
  ```
- Providing there are no error, then copy the follwing:
  ```
  java Eurovision
  ```
- This should start it running, so enjoy!

If finding any bugs or if it doesn't work for any reason, please feel free to reach out and let me know.
