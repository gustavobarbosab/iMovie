# 🎥 iMovie

This project demonstrates an implementation of the [**Model-View-Intent (MVI)**](https://medium.com/swlh/mvi-architecture-with-android-fcde123e3c4a) architecture and follows [**Clean Architecture**](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/) principles to separate concerns between different layers. It highlights my skills in structured code organization, tests, and modern Android libraries.

https://github.com/user-attachments/assets/b6613375-44c5-4ca7-b2ee-568c40f6c366

---

## 💡 Components

In this project, we use some of the best libraries to create an Android project.

- [X] Compose
- [X] Material 3
- [X] Navigation
- [X] Hilt
- [X] Coil
- [X] Coroutines
- [X] Retrofit
- [X] Gson
- [X] Hamcrest
- [X] Mockk
- [X] MVI - Model-View-Intent
- [X] Dark and Light themes
- [ ] Modularization
- [ ] Composite build
- [ ] UI Tests
- [ ] Detekt
- [ ] Ktlint

--- 

## 📐 Architecture

This project uses **Clean Architecture** with **MVI** (Model-View-Intent) pattern to separate concerns, scalability, and testability. Here’s an outline of each layer:

<img height="500" alt="image" src="https://github.com/user-attachments/assets/69e8c015-d2a6-4585-bbcd-e70002b43666">

---

### 📺 Presentation Layer:
<img height="500" alt="image" src="https://github.com/user-attachments/assets/444d97f7-fd63-406a-a76d-263f8202d843">

Let's dive into each component of this layer:
- **Screen**: This class represents the start point of the screen. It receives the ViewModel and the Navigator as parameters.
- **ScreenContent**: This one contains the layout and is created to make the screen easily testable once we inject all the dependencies.
- **ScreenState**: As the name suggests, this indicates the screen's state, including all necessary parameters for its operation.
- **ViewModel**: The ViewModel class holds and manages UI-related data; it receives the `Intent`, performs `Actions`, and generates `Results` to be reduced in the new screen `State`.
- **Reducer**: Reducer is responsible for creating the new screen `State` based on the `Results` received from the `ViewModel`.
- **Result**: This class is the result of the actions on the `ViewModel`, it will be interpreted by the Reducer to generate a new `ScreenState`.
- **SideEffectProcessor**: This class generates side effects based on the `Intents`(preSideEffect) and `Result`(postSideEffect).
- **SideEffect**: is like the [SingleLiveEvent](https://abhiappmobiledeveloper.medium.com/android-singleliveevent-of-livedata-for-ui-event-35d0c58512da), which is responsible for emitting unique events to be handled by the view.

---

### 💾 Data Layer:
The data layer handles data sources and retrieval, whether from APIs, local databases, or other sources. Its primary purpose is to provide data to the domain layer in a standardized format.
This layer contains the following components:
- **Repositories**: Repositories are the main components of the data layer, providing a clean API for data access and managing data sources (network, database, cache).
- **Data Sources**: These handle specific data sources, such as RemoteDataSource for API calls and LocalDataSource for database access.
- **Mappers**: Convert data entities (like API or database DTOs) into domain entities, keeping the data structure consistent with domain needs.

---

### 📚 Domain Layer:
The domain layer focuses on the business logic and is independent of frameworks. It defines how the app operates and interacts with data, which multiple presentation layers can use.
- **Use Cases (or Interactors)**: Encapsulate a specific business action or logic (e.g., fetching a movie detail or saving a record). Use cases pull data from the repository and process it according to business rules.
- **Domain Entities**: Represent core business entities, passed between layers and kept independent of specific data formats.
- *Repository Interface*: It defines a contract for data operations but doesn’t specify how data is fetched or stored. This approach allows the domain layer to remain independent of specific data sources, making the architecture more modular and testable.

---

### ➿ Dependency between the layers

This image shows the dependency between the layers, following Uncle Bob's explanation in [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
| IMovie | Clean Architecture |
| --- | --- 
| <img width="693" alt="image" src="https://github.com/user-attachments/assets/47ebe0ab-16cd-462b-bfe7-8e80170633ae"> | <img width="693" alt="image" src="https://github.com/user-attachments/assets/60ddbbae-409f-4fba-9b93-1844119b88d2"> |

---

## 🔨Project structure

The project structure is divided into the following packages, as explained before.

> [!IMPORTANT]
> This project was implemented in 4 days, so it is not modular. The future idea is to migrate the current packages to different modules following the strategy implemented in [this repository](https://github.com/gustavobarbosab/android-compose-playground).

<img height="500" alt="image" src="https://github.com/user-attachments/assets/22804089-1881-496e-b6ed-e94ef9c7ea4f">

Some explanations about the packages that will be modules in the future:
- **common**: This package includes reusable components and utilities, like extensions and common UI components, which can be shared across multiple applications.
- **core**: This package contains the app's foundational elements, including network configurations, interceptors, and other essential configurations.
- **data/domain/presentation**: In the modular setup, these packages will be split across feature modules:
  - Each feature module will consist of:
    - *presentation*: Housing all UI-related logic specific to the feature.
    - *shared*: Containing shared components and business logic that other features can access.

---

## 💻 Setup Instructions

This project setup is simple, and you can follow these steps:
- Clone the project
- Access or create an account on [TMDB](https://developer.themoviedb.org)
- Copy your API Key Auth on [TMDB Developer Auth](https://developer.themoviedb.org/reference/intro/authentication)
- Paste the API Key on your `local.properties` as the parameter `tmdb.api.key`.
- Run the project =D

Example of the local.properties file:
```local.properties
  tmdb.api.key="your-key-here"
```

## 📖 Some References:

- [Model-View-Intent (MVI)](https://medium.com/swlh/mvi-architecture-with-android-fcde123e3c4a)
- [Clean Architecture - Fernando Cejas](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)
- [Clean Architecture - Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [SOLID](https://blog.cleancoder.com/uncle-bob/2020/10/18/Solid-Relevance.html)

## 🏆 Other good projects:
- [android-compose-playground](https://github.com/gustavobarbosab/android-compose-playground) - A modular project with composite builds, using strategies for sharing rules based on clean architecture principles.
- [gubank](https://github.com/gustavobarbosab/gubank) - I’m planning to start a channel to teach a bit about Android. In this repo, I run unit and instrumented tests to show how things work in real scenarios.
- [netscan](https://github.com/gustavobarbosab/netscan) - A library I built from scratch with tools for scanning computer networks.
- [movies](https://github.com/gustavobarbosab/movies) - An old project from three years ago, where I explored some features and applied clean architecture and modularization concepts.

## 😄 Become a contributor

Do you wish to be part of this project? Open a pull request with your contribution.




