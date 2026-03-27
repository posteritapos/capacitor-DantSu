// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorDantsu",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorDantsu",
            targets: ["DantSuPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "DantSuPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/DantSuPlugin"),
        .testTarget(
            name: "DantSuPluginTests",
            dependencies: ["DantSuPlugin"],
            path: "ios/Tests/DantSuPluginTests")
    ]
)