licenses(["notice"])  # Apache 2.0

java_library(
    name = "plugin_dev",
    srcs = glob(["src/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//base",
        "//intellij_platform_sdk:devkit",
        "//intellij_platform_sdk:plugin_api",
        "//java",
        "//proto:proto_deps",
        "//sdkcompat",
        "@jsr305_annotations//jar",
    ],
)

filegroup(
    name = "plugin_xml",
    srcs = ["src/META-INF/blaze-plugin-dev.xml"],
    visibility = ["//visibility:public"],
)

load(
    "//build_defs:build_defs.bzl",
    "merged_plugin_xml",
    "stamped_plugin_xml",
    "intellij_plugin",
)

merged_plugin_xml(
    name = "merged_plugin_xml",
    srcs = [
        "//base:plugin_xml",
        "//java:plugin_xml",
    ] + [
        ":plugin_xml",
    ],
)

stamped_plugin_xml(
    name = "plugin_dev_plugin_xml",
    plugin_id = "com.google.idea.blaze.plugin_dev",
    plugin_name = "com.google.idea.blaze.plugin_dev",
    plugin_xml = "merged_plugin_xml",
)

intellij_plugin(
    name = "plugin_dev_integration_test_plugin",
    testonly = 1,
    plugin_xml = ":plugin_dev_plugin_xml",
    deps = [
        ":plugin_dev",
        "//base",
        "//java",
    ],
)

load(
    "//testing:test_defs.bzl",
    "intellij_integration_test_suite",
)

intellij_integration_test_suite(
    name = "integration_tests",
    srcs = glob(["tests/integrationtests/**/*.java"]),
    required_plugins = "com.google.idea.blaze.ijwb",
    test_package_root = "com.google.idea.blaze.plugin",
    runtime_deps = [
        ":plugin_dev_integration_test_plugin",
    ],
    deps = [
        ":plugin_dev",
        "//base",
        "//base:integration_test_utils",
        "//base:unit_test_utils",
        "//intellij_platform_sdk:plugin_api_for_tests",
        "@jsr305_annotations//jar",
        "@junit//jar",
    ],
)
