# Grid Image Search (google image search)

Extend the Google Image Search that we built in class to allow a user to modify advanced search options and result pagination.

Time spent: 10 hours spent in total

Completed user stories:

* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters such as:
 - Size (small, medium, large, extra-large)
 - Color filter (black, blue, brown, gray, green, etc...)
 - Type (faces, photo, clip art, line art)
 - Site (espn.com)
 - Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

* [ ] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [ ] Advanced: User can share an image to their friends or email it to themselves
* [ ] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* [ ] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* [ ] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [ ] Bonus: Use the StaggeredGridView to display visually interesting image results
* [ ] Bonus: User can zoom or pan images displayed in full-screen detail view
 
Notes:


Walkthrough of all user stories:

![Video Walkthrough](gridImageSeach.gif)