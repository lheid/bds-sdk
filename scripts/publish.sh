#!/bin/bash
cd ..
# Add '.dev' on the version name. Non dev builds are done though jenkins build only
export BUILD_NAME_SUFFIX=".dev"

./gradlew :android-appcoins-billing:clean :appcoins-billing:clean
./gradlew :android-appcoins-billing:assemble :appcoins-billing:assemble
./gradlew publish bintrayUpload