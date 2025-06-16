# 🌼 DAISY - Dermatological AI System for Your Skin

![DAISY Banner](./daisy (1).png)

**DAISY** is an AI-powered dermatological diagnostic system designed to help users quickly and confidently identify skin conditions such as *tinea versicolor* (panu) and *ringworm* through mobile image analysis.

> 🎓 Capstone Project — Bangkit Academy 2024 Batch 2

---

## 📱 Features

- 🔐 **Authentication System**  
  - Register, Login, and Reset Password
- 🤖 **AI-based Diagnosis**  
  - Upload or capture image of skin condition  
  - Deep Learning model predicts skin disease  
  - Confidence-based treatment recommendations
- ✂️ **Image Cropping with UCrop**
- 👤 **User Profile Management**  
  - View and edit basic user details  
  - Logout and account-related settings

---

## 🏗️ Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM (Model-View-ViewModel)  
- **Jetpack Components**: LiveData, ViewModel, Navigation Component  
- **Networking**: Retrofit  
- **Image Cropping**: UCrop  
- **Machine Learning Integration**: TensorFlow Lite (.tflite model)  
- **Cloud & Auth**: Firebase Authentication & Firebase Storage

---

## 🧠 Machine Learning

- Trained using a dataset of labeled skin condition images
- Output: Predicted disease and recommended treatment
- Accuracy: **74%**
- Format: TensorFlow Lite (.tflite)

---

## 📂 Project Structure (MVVM)

```
com.example.daisyapp
│
├── data
│   ├── adapter/           # RecyclerView adapters and view holders
│   ├── helper/            # Helper classes and utilities
│   ├── injection/         # Dependency injection providers
│   ├── model/             # Data models
│   ├── preference/        # SharedPreferences handler
│   ├── repository/        # Repository pattern implementation
│   ├── response/          # API response models
│   ├── retrofit/          # Retrofit API service
│   ├── room/              # Local database access objects (DAO)
│   └── utils/             # General utility classes
│
├── view
│   ├── customview/        # Custom UI components (e.g., buttons, dialogs)
│   ├── ui/                # UI fragments/activities for login, scan, profile, etc.
│   └── viewmodel/         # ViewModel classes handling UI logic
│
└── MainActivity.kt        # Entry point of the application
```

---

## 🚀 Getting Started

1. **Clone this repository**
   ```bash
   git clone https://github.com/your-username/daisy-dermatology-app.git
   ```

2. **Open in Android Studio**

3. **Set up Firebase**
   - Add `google-services.json` to `app/` directory
   - Enable Firebase Authentication and Storage

4. **Run the app** on a device or emulator

---

## 🤝 Team & Contributions

This application was built as a collaborative Capstone Project under Bangkit Academy 2024 Batch 2, involving:

- Mobile Development Team (Kotlin + MVVM)
- Machine Learning Team (Model training + TFLite)
- Cloud Computing Team (Firebase + Hosting)
- UI/UX Team (Mockups & Design)

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
