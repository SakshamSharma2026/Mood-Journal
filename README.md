
# Mood Journal


[![Static Badge](https://img.shields.io/badge/Kotlin-black?logo=kotlin)]()
[![Static Badge](https://img.shields.io/badge/Jetpack--compose-black?logo=jetpack%20compose)]()
[![Static Badge](https://img.shields.io/badge/Download--apk-black?logo=android)](https://github.com/SakshamSharma2026/Mood-Journal/releases)
[![Android CI Build](https://github.com/SakshamSharma2026/Mood-Journal/actions/workflows/ci_build.yml/badge.svg)](https://github.com/SakshamSharma2026/Mood-Journal/actions/workflows/ci_build.yml)

![App Screenshot](https://github.com/SakshamSharma2026/Mood-Journal/blob/main/ss/ss.png?raw=true)




### Mood Journal is a sleek and feature-rich Android app built with Jetpack Compose, designed to help users track their journaling and daily experiences effortlessly. This app provides a clean and intuitive user interface with a variety of customization options.

## Features ✨-
- Custom Date Selection
- New Prompts
- Color Selection
- Add Image
- Delete Journal Entry
- Edit Journal Entry
- Share
- Clean UI


## Developed Using 👨🏻‍💻

- [Android Architecture Components](https://developer.android.com/topic/architecture)   Collection of libraries that help you design robust, testable, and maintainable apps.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) Modern Android UI toolkit for building native UIs.
- [Coil](https://coil-kt.github.io/coil/)  Image loading library for efficient handling of images in the app.
- [ViewModel]() - Stores UI-related data that isn't destroyed on UI changes.
- [Dagger-Hilt](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Asynchronous programming to handle background tasks seamlessly.
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs. Basically it's a replacement for SharedPreferences.


## Project Root
```
├── JournalApp.kt
├── di
│   └── AppModule.kt
├── feature_journal
│   ├── data
│   │   ├── data_source
│   │   │   ├── JournalDao.kt
│   │   │   └── JournalDatabase.kt
│   │   └── repository
│   │       ├── DataStoreOperationsRepositoryImpl.kt
│   │       └── JournalRepositoryImpl.kt
│   ├── domain
│   │   ├── model
│   │   │   └── JournalDataResponse.kt
│   │   ├── repository
│   │   │   ├── DataStoreOperationsRepository.kt
│   │   │   └── JournalRepository.kt
│   │   ├── use_case
│   │   │   ├── datastore_operations
│   │   │   │   ├── DataStoreOperationsUseCases.kt
│   │   │   │   ├── ReadFirstEntry.kt
│   │   │   │   ├── ReadOnBoarding.kt
│   │   │   │   ├── ReadUserName.kt
│   │   │   │   ├── SaveFirstEntry.kt
│   │   │   │   ├── SaveOnBoarding.kt
│   │   │   │   └── SaveUserName.kt
│   │   │   └── journal
│   │   │       ├── AddJournal.kt
│   │   │       ├── DeleteJournal.kt
│   │   │       ├── GetJournalById.kt
│   │   │       ├── GetJournals.kt
│   │   │       └── JournalUseCases.kt
│   │   └── util
│   │       ├── add_edit_journal
│   │       │   ├── AddEditJournalEvent.kt
│   │       │   ├── AddEditJournalState.kt
│   │       │   └── JournalRandomDataState.kt
│   │       ├── datastore
│   │       │   ├── DataStoreOperationsEvent.kt
│   │       │   └── DataStoreOperationsState.kt
│   │       └── journal
│   │           ├── JournalDataTypeConverter.kt
│   │           ├── JournalEvent.kt
│   │           ├── JournalState.kt
│   │           └── JournalTextFieldState.kt
│   ├── navigation
│   │   ├── Screen.kt
│   │   └── SetupNavGraph.kt
│   ├── presentation
│   │   ├── DataStoreOperationsViewModel.kt
│   │   ├── MainActivity.kt
│   │   ├── add_edit_journal
│   │   │   ├── AddEditJournalScreen.kt
│   │   │   ├── AddEditJournalViewModel.kt
│   │   │   ├── components
│   │   │   │   ├── CustomHintTextField.kt
│   │   │   │   ├── DatePickerView.kt
│   │   │   │   ├── ShowColorPicker.kt
│   │   │   │   ├── ShowSearchBar.kt
│   │   │   │   ├── ShowVoiceRecorder.kt
│   │   │   │   └── VerticalDivider.kt
│   │   │   └── util
│   │   │       ├── AndroidAudioPlayer.kt
│   │   │       ├── AndroidAudioRecorder.kt
│   │   │       ├── AudioPlayer.kt
│   │   │       └── AudioRecorder.kt
│   │   ├── journal_screen
│   │   │   ├── JournalScreen.kt
│   │   │   ├── JournalViewModel.kt
│   │   │   └── components
│   │   │       ├── CustomDropDownMenu.kt
│   │   │       ├── JournalCardItem.kt
│   │   │       └── PandaCardView.kt
│   │   ├── onboarding_screen
│   │   └── OnBoardingScreen.kt
│   │   └── splash_screen
│   │       └── SplashScreen.kt
│   └── util
│       ├── Constants.kt
│       └── Utility.kt
└── ui
    └── theme
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```


## Buy Me a Coffee ☕
If you find Mood Journal helpful and would like to support further development, you can [Buy Me a Coffee](https://www.buymeacoffee.com/Saksham2026).


## License
This project is licensed under the MIT License.

## Acknowledgments
Special thanks to Jetpack Compose for making UI development on Android a breeze!
## Feedback

If you have any feedback, please reach out to us at developer.saksham26@gmail.com
