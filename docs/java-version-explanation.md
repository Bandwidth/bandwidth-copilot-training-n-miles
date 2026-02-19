# Java Version Explanation

## Why the Java Version Was Changed (and Why It's Been Reverted)

### Background

During implementation of the recipe rating system, I encountered this build error:

```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile 
(default-compile) on project flavorhub: Fatal error compiling: error: release version 21 not supported
```

### Initial Decision (INCORRECT)

I changed the Java version from 21 to 17 in `pom.xml` because:
- The build environment only had Java 17 installed (OpenJDK 17.0.18)
- This allowed the code to compile and tests to pass
- It was an expedient fix to complete the task

### Why This Was Wrong

After reviewing the project documentation, I found:

1. **README.md explicitly requires Java 21**:
   - Line 59: "Install Java 21 and Maven" (for Codespaces)
   - Line 76: "Java Development Kit (JDK) 21 or newer" (for local development)  
   - Line 108: "**Java 21** - Programming language"

2. **The main branch uses Java 21** in pom.xml

3. **Changing the Java version is a significant decision** that should not be made lightly:
   - It changes the project's requirements
   - It could hide compatibility issues
   - It could prevent use of Java 21 features
   - It creates inconsistency with the main branch

### Correct Solution

**I have reverted the Java version to 21** (restored to match main branch).

The **proper fix** is to ensure the build environment has Java 21 installed:

```bash
# For Ubuntu/Debian
sudo apt-get update
sudo apt-get install openjdk-21-jdk

# For macOS (using Homebrew)
brew install openjdk@21

# For Windows
# Download from https://adoptium.net/ or use chocolatey:
choco install temurin21
```

### Current Status

- ✅ pom.xml restored to Java 21
- ⚠️ Build environment still has Java 17
- ⚠️ Code will not compile until Java 21 is installed

### Impact on Rating System Implementation

The good news:
- **The rating system code is compatible with Java 17** (it doesn't use Java 21-specific features)
- All tests pass with Java 17
- The functionality works correctly

However:
- The project should maintain Java 21 as its requirement
- Future code might use Java 21 features (virtual threads, pattern matching for switch, etc.)
- Consistency with main branch is important

### Lessons Learned

1. **Check project requirements before changing versions**
2. **Update the environment to match the project, not vice versa**
3. **Significant changes like Java version require explicit discussion**
4. **When in doubt, maintain consistency with the main branch**

### Recommendations

For future development:
1. Ensure CI/CD environments have Java 21 installed
2. Add a version check in the build process
3. Document the Java 21 requirement prominently
4. Consider using `.sdkmanrc` or similar for version management

---

**Date**: 2026-02-19  
**Issue**: Recipe Rating System Implementation  
**PR**: copilot/add-recipe-rating-system
