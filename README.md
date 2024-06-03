# Minimal News App

Minimal & Efficient news app that fetches news from [newsapi.org](https://newsapi.org/). Built with Clean Architecture driven use cases and best practices.

## Features

- **Modern UI**: Built with Jetpack Compose.
- **Dependency Injection**: Powered by Dagger Hilt.
- **Navigation**: Seamless navigation with Compose Navigation.
- **User Preferences**: Managed with Preferences Datastore.
- **Onboarding Flow**: User-friendly onboarding experience.
- **Data Storage**: RoomDB for efficient disk caching.
- **Network Requests**: Retrofit for reliable network communication.

## Table of Contents

- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Screenshots

_(Include screenshots of the app here)_

## Architecture

This project follows the principles of Clean Architecture, which ensures separation of concerns and scalability. The architecture is divided into several layers:

- **Presentation Layer**: Contains UI components and state management.
- **Domain Layer**: Contains use cases and business logic.
- **Data Layer**: Responsible for data management, including repository pattern implementation.

## Tech Stack

- **Jetpack Compose**: Modern toolkit for building native UI.
- **Compose Splash API**: For a smooth splash screen experience.
- **Dagger Hilt**: Dependency Injection framework.
- **Compose Navigation**: For handling navigation in a Compose world.
- **Preferences Datastore**: Jetpack's datastore for storing user preferences.
- **RoomDB**: Database for offline caching.
- **Retrofit**: Type-safe HTTP client for Android and Java.

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/xidsyed/NewsBasket.git
    ```

2. Navigate to the project directory:
    ```sh
    cd NewsBasket
    ```

3. Open the project in Android Studio.

4. Add your `newsapi.org` API key to the `local.properties` file:
    ```properties
    NEWS_API_KEY=your_api_key_here
    ```

5. Build and run the app.

## Usage

- **Onboarding**: The app guides new users through an onboarding flow to personalize their experience.
- **News Feed**: Browse the latest news articles fetched from [newsapi.org](https://newsapi.org/).
- **Favorites**: Save favorite articles for later reading.
- **Settings**: Customize user preferences and app settings.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch:
    ```sh
    git checkout -b feature/your-feature-name
    ```
3. Make your changes and commit them:
    ```sh
    git commit -m 'Add some feature'
    ```
4. Push to the branch:
    ```sh
    git push origin feature/your-feature-name
    ```
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

Feel free to open issues or submit feature requests. Happy coding!
