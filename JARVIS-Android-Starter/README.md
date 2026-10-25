# JARVIS Android Starter (v0.1)

A beginner-friendly starting point for a local-first Android AI companion.

## What is included
- Jetpack Compose chat-style interface
- A protected core boundary (`JarvisCore`)
- A small capability registry for approved tools
- Clear extension points for local AI, voice, avatar, and device actions

## What is NOT included yet
This starter does not contain a language model, live microphone service, animated avatar, online AI provider, or broad device automation. Those are later milestones and should be added one at a time.

## Build
1. Install Android Studio: https://developer.android.com/studio
2. Extract this ZIP.
3. In Android Studio choose **Open** and select the `JARVIS-Android-Starter` folder.
4. Allow Gradle to sync and install the suggested Android SDK if prompted.
5. Run the `app` configuration on an Android emulator or connected phone.

Recommended: Android Studio with JDK 17. This project uses Android Gradle Plugin 8.7.3 and Gradle 8.9.

## Protected-core rule
Do not let generated scripts edit `app/src/main/java/com/example/jarvis/core/`. New capabilities belong in isolated modules/packages and must be reviewed and tested before being registered. The UI currently uses a mock response; it does not claim to be an AI model.

## Next milestones
1. Add a user-selected local inference runtime and a small compatible model.
2. Add speech input/output with explicit microphone permission and visible listening state.
3. Add a 2D avatar.
4. Add a permissioned action broker (start with opening apps).
5. Add a sandboxed capability builder and tests.
6. Add optional online model adapters with user-configurable privacy policy.


## Build from your phone using GitHub Actions
This ZIP includes `.github/workflows/android-apk.yml`. After uploading the extracted project to a GitHub repository:
1. Open the repository on GitHub in your mobile browser.
2. Open **Actions** and enable workflows if GitHub asks.
3. Select **Build JARVIS APK** and tap **Run workflow** (or push a commit to `main`).
4. Open the completed workflow run and download the `jarvis-debug-apk` artifact.
5. Extract the downloaded artifact ZIP, then open `app-debug.apk` on your phone and approve installation if Android prompts you.

The cloud build requires internet and a GitHub account. GitHub Actions usage limits/quotas may apply. The APK is a debug build for testing, not a Play Store release. Review the workflow before running it. The app remains a demo UI until the AI, voice, avatar, and device-action modules are implemented.
