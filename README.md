# Project 1 - **NYTimes News**

**NYTimes News** shows the news online that follow the query from user. The app utilizes the New York Times Search Database API to display images and basic information about these articles to the user.

Time spent: **35** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can view a list of articles (thumbnail, headline and snippet) that follow user's query by New York Times Search API.
* [x] List item with the [RecyclerView](https://guides.codepath.com/android/Using-the-RecyclerView).
* [x] For each articles displayed, user can see the following details:
  * [x] Thumbnail (if available), Headline (Portrait mode)
  * [x] Thumbnail (if available), Headline, Snippet (Landscape mode)
* [x] Can configure advanced search filters:
  * [x] Begin Date with using a [Date Picker](https://guides.codepath.com/android/Using-DialogFragment#displaying-date-or-time-)
  * [x] Sort order (oldest or newest) with using a [Spinner Dropdown](https://guides.codepath.com/android/Working-with-Input-Views#spinners)
  * [x] News desk values (Arts, Fashion & Style, Sports) with using [SwitchCompat](https://developer.android.com/reference/android/support/v7/widget/SwitchCompat)
* [x] Subsequent searches will have any filters applied to the search results. 
* [x] Tap on any article in results to view the contents in an embedded browser.
* [x] Can scroll down [infinitely](https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) to continue loading more news articles. The maximum number of articles is limited by the API search.


The following **optional** features are implemented:

* [x] Use the [ActionBar SearchView](https://guides.codepath.com/android/Extended-ActionBar-Guide#adding-searchview-to-actionbar).
* [x] Replace Filter Settings Activity with a lightweight (modal overlay)[(https://github.com/afollestad/material-dialogs)].
* [x] Use the RecyclerView with the StaggeredGridLayoutManager.
* [x] Replace all icon drawables and other static image assets with vector drawables where appropriate.
* [x] Use Parcelable instead of Serializable using the popular (Parceler library)[https://guides.codepath.com/android/Using-Parceler].
* [x] Replace the embedded WebView with [Chrome Custom Tabs](https://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing.
* [x] User can share a link to their friends or email it to themselves.

The following **bonus** features are implemented:

* [x] Robust error handling, check if internet is available, handle error cases, network failures.
* [x] When viewing a article that not have available thumbnail, app only show the headline and snippet with uses [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [x] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [x] Switch to using [Retrolambda Expressions](https://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks


The following **additional** features are implemented:

* [x] [MVP]() - An architectural patterns for app development in Android.
* [x] Use [ConstraintLayout](https://guides.codepath.com/android/Constructing-View-Layouts#1-building-via-constraintlayout) to display layouts.
* [x] Use [SharedPreferences](https://guides.codepath.com/android/Storing-and-Accessing-SharedPreferences) to store settings data from user. 


## Video Walkthrough

Here's a walkthrough of implemented user stories:

[Video walkthrough](https://i.imgur.com/CFAYDpq.gif) (Due to heavy size of gif file)

<img src='https://i.imgur.com/bY7dZFu.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Challenges encountered while building the app:
- Have to reload the data from the API when rotate the screen to show exactly layout.
- Not optimize app completely with Butterknife annotation library.
- SharedPreferences is not configured suitable.

## Open-source libraries used

- [Retrofit](http://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Glide](https://bumptech.github.io/glide/) - A fast and efficient image loading library for Android 
- [Material Dialog](https://github.com/afollestad/material-dialogs) - A beautiful, fluid, and customizable dialogs API. 

## License

    Copyright 2018 Le Anh Thien

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
