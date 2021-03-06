/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.idea.blaze.android.run.test;

import static com.android.tools.idea.testartifacts.instrumented.AndroidTestRunConfiguration.TEST_ALL_IN_MODULE;
import static com.android.tools.idea.testartifacts.instrumented.AndroidTestRunConfiguration.TEST_ALL_IN_PACKAGE;
import static com.android.tools.idea.testartifacts.instrumented.AndroidTestRunConfiguration.TEST_CLASS;
import static com.android.tools.idea.testartifacts.instrumented.AndroidTestRunConfiguration.TEST_METHOD;

import com.google.idea.blaze.base.run.state.RunConfigurationState;
import com.google.idea.blaze.base.run.state.RunConfigurationStateEditor;
import com.google.idea.blaze.base.settings.Blaze;
import com.google.idea.blaze.base.ui.UiUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBLabel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * The part of the Blaze Android Test handler editor that allows the user to pick test filters.
 * Forked from {@link org.jetbrains.android.run.testing.TestRunParameters}.
 */
class BlazeAndroidTestRunConfigurationStateEditor implements RunConfigurationStateEditor {
  private final RunConfigurationStateEditor commonStateEditor;

  private JRadioButton allInPackageButton;
  private JRadioButton classButton;
  private JRadioButton testMethodButton;
  private JRadioButton allInTargetButton;
  private LabeledComponent<EditorTextField> packageComponent;
  private LabeledComponent<EditorTextField> classComponent;
  private LabeledComponent<EditorTextField> methodComponent;
  private JPanel panel;
  private LabeledComponent<EditorTextField> runnerComponent;
  private JBLabel labelTest;
  private JCheckBox runThroughBlazeTestCheckBox;
  private final JRadioButton[] testingType2RadioButton = new JRadioButton[4];

  private boolean componentEnabled = true;

  BlazeAndroidTestRunConfigurationStateEditor(
      RunConfigurationStateEditor commonStateEditor, Project project) {
    this.commonStateEditor = commonStateEditor;
    setupUI(project);

    packageComponent.setComponent(new EditorTextField());

    classComponent.setComponent(new EditorTextField());

    runnerComponent.setComponent(new EditorTextField());

    methodComponent.setComponent(new EditorTextField());

    addTestingType(BlazeAndroidTestRunConfigurationState.TEST_ALL_IN_TARGET, allInTargetButton);
    addTestingType(TEST_ALL_IN_PACKAGE, allInPackageButton);
    addTestingType(TEST_CLASS, classButton);
    addTestingType(TEST_METHOD, testMethodButton);
  }

  private void addTestingType(final int type, JRadioButton button) {
    testingType2RadioButton[type] = button;
    button.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            updateLabelComponents(type);
          }
        });
  }

  private void updateButtonsAndLabelComponents(int type) {
    allInTargetButton.setSelected(type == TEST_ALL_IN_MODULE);
    allInPackageButton.setSelected(type == TEST_ALL_IN_PACKAGE);
    classButton.setSelected(type == TEST_CLASS);
    testMethodButton.setSelected(type == TEST_METHOD);
    updateLabelComponents(type);
  }

  private void updateLabelComponents(int type) {
    packageComponent.setVisible(type == TEST_ALL_IN_PACKAGE);
    classComponent.setVisible(type == TEST_CLASS || type == TEST_METHOD);
    methodComponent.setVisible(type == TEST_METHOD);
  }

  private void setupUI(Project project) {
    try {
      doSetupUI(project);
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      // Can't happen - this is from IJ generated code
    }
  }

  /** Initially generated by IntelliJ from a .form file, then checked in. */
  private void doSetupUI(Project project)
      throws ClassCastException, InstantiationException, IllegalAccessException,
          ClassNotFoundException {
    panel = new JPanel();
    panel.setLayout(new GridLayoutManager(6, 6, new Insets(0, 0, 0, 0), -1, -1));
    panel.setAlignmentX(0.0f);
    allInPackageButton = new JRadioButton();
    allInPackageButton.setActionCommand(
        ResourceBundle.getBundle("messages/ExecutionBundle")
            .getString("jnit.configuration.all.tests.in.package.radio"));
    this.loadButtonText(
        allInPackageButton,
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.all.in.package.radio"));
    panel.add(
        allInPackageButton,
        new GridConstraints(
            1,
            2,
            1,
            1,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    classButton = new JRadioButton();
    classButton.setActionCommand(
        ResourceBundle.getBundle("messages/ExecutionBundle")
            .getString("junit.configuration.test.class.radio"));
    classButton.setEnabled(true);
    classButton.setSelected(false);
    this.loadButtonText(
        classButton,
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.class.radio"));
    panel.add(
        classButton,
        new GridConstraints(
            1,
            3,
            1,
            1,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    testMethodButton = new JRadioButton();
    testMethodButton.setActionCommand(
        ResourceBundle.getBundle("messages/ExecutionBundle")
            .getString("junit.configuration.test.method.radio"));
    testMethodButton.setSelected(false);
    this.loadButtonText(
        testMethodButton,
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.method.radio"));
    panel.add(
        testMethodButton,
        new GridConstraints(
            1,
            4,
            1,
            1,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    labelTest = new JBLabel();
    labelTest.setHorizontalAlignment(2);
    labelTest.setHorizontalTextPosition(2);
    labelTest.setIconTextGap(4);
    this.loadLabelText(
        labelTest,
        ResourceBundle.getBundle("messages/ExecutionBundle")
            .getString("junit.configuration.configure.junit.test.label"));
    panel.add(
        labelTest,
        new GridConstraints(
            1,
            0,
            1,
            1,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    final Spacer spacer1 = new Spacer();
    panel.add(
        spacer1,
        new GridConstraints(
            1,
            5,
            1,
            1,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_WANT_GROW,
            1,
            null,
            null,
            null,
            0,
            false));
    allInTargetButton = new JRadioButton();
    allInTargetButton.setText("All in test target");
    allInTargetButton.setMnemonic('A');
    allInTargetButton.setDisplayedMnemonicIndex(0);
    panel.add(
        allInTargetButton,
        new GridConstraints(
            1,
            1,
            1,
            1,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    classComponent = new LabeledComponent();
    classComponent.setComponentClass("javax.swing.JPanel");
    classComponent.setLabelLocation("West");
    classComponent.setText(
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.class.label"));
    panel.add(
        classComponent,
        new GridConstraints(
            3,
            0,
            1,
            6,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    methodComponent = new LabeledComponent();
    methodComponent.setComponentClass(
        "com.intellij.openapi.ui.TextFieldWithBrowseButton$NoPathCompletion");
    methodComponent.setLabelLocation("West");
    methodComponent.setText(
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.method.label"));
    panel.add(
        methodComponent,
        new GridConstraints(
            4,
            0,
            1,
            6,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    runnerComponent = new LabeledComponent();
    runnerComponent.setComponentClass("javax.swing.JPanel");
    runnerComponent.setEnabled(true);
    runnerComponent.setLabelLocation("North");
    runnerComponent.setText(
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.test.run.configuration.instrumentation.label"));
    panel.add(
        runnerComponent,
        new GridConstraints(
            5,
            0,
            1,
            6,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    packageComponent = new LabeledComponent();
    packageComponent.setComponentClass("javax.swing.JPanel");
    packageComponent.setLabelLocation("West");
    packageComponent.setText(
        ResourceBundle.getBundle("messages/AndroidBundle")
            .getString("android.run.configuration.package.label"));
    panel.add(
        packageComponent,
        new GridConstraints(
            2,
            0,
            1,
            6,
            GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    runThroughBlazeTestCheckBox = new JCheckBox();
    runThroughBlazeTestCheckBox.setText(
        String.format("Run through '%s test'", Blaze.buildSystemName(project).toLowerCase()));
    runThroughBlazeTestCheckBox.setToolTipText(
        String.format("Slower, but more truthful to %s", Blaze.buildSystemName(project)));
    panel.add(
        runThroughBlazeTestCheckBox,
        new GridConstraints(
            0,
            0,
            1,
            6,
            GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null,
            null,
            null,
            0,
            false));
    ButtonGroup buttonGroup;
    buttonGroup = new ButtonGroup();
    buttonGroup.add(allInPackageButton);
    buttonGroup.add(classButton);
    buttonGroup.add(testMethodButton);
    buttonGroup.add(allInTargetButton);
  }

  /** Initially generated by IntelliJ from a .form file, then checked in. */
  private void loadLabelText(JLabel component, String text) {
    StringBuffer result = new StringBuffer();
    boolean haveMnemonic = false;
    char mnemonic = '\0';
    int mnemonicIndex = -1;
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '&') {
        i++;
        if (i == text.length()) {
          break;
        }
        if (!haveMnemonic && text.charAt(i) != '&') {
          haveMnemonic = true;
          mnemonic = text.charAt(i);
          mnemonicIndex = result.length();
        }
      }
      result.append(text.charAt(i));
    }
    component.setText(result.toString());
    if (haveMnemonic) {
      component.setDisplayedMnemonic(mnemonic);
      component.setDisplayedMnemonicIndex(mnemonicIndex);
    }
  }

  /** Initially generated by IntelliJ from a .form file and checked in. */
  private void loadButtonText(AbstractButton component, String text) {
    StringBuffer result = new StringBuffer();
    boolean haveMnemonic = false;
    char mnemonic = '\0';
    int mnemonicIndex = -1;
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '&') {
        i++;
        if (i == text.length()) {
          break;
        }
        if (!haveMnemonic && text.charAt(i) != '&') {
          haveMnemonic = true;
          mnemonic = text.charAt(i);
          mnemonicIndex = result.length();
        }
      }
      result.append(text.charAt(i));
    }
    component.setText(result.toString());
    if (haveMnemonic) {
      component.setMnemonic(mnemonic);
      component.setDisplayedMnemonicIndex(mnemonicIndex);
    }
  }

  private int getTestingType() {
    for (int i = 0, myTestingType2RadioButtonLength = testingType2RadioButton.length;
        i < myTestingType2RadioButtonLength;
        i++) {
      JRadioButton button = testingType2RadioButton[i];
      if (button.isSelected()) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void applyEditorTo(RunConfigurationState genericState) {
    BlazeAndroidTestRunConfigurationState state =
        (BlazeAndroidTestRunConfigurationState) genericState;
    commonStateEditor.applyEditorTo(state.getCommonState());

    state.setRunThroughBlaze(runThroughBlazeTestCheckBox.isSelected());

    state.setTestingType(getTestingType());
    state.setClassName(classComponent.getComponent().getText());
    state.setMethodName(methodComponent.getComponent().getText());
    state.setPackageName(packageComponent.getComponent().getText());
    state.setInstrumentationRunnerClass(runnerComponent.getComponent().getText());
  }

  @Override
  public void resetEditorFrom(RunConfigurationState genericState) {
    BlazeAndroidTestRunConfigurationState state =
        (BlazeAndroidTestRunConfigurationState) genericState;
    commonStateEditor.resetEditorFrom(state.getCommonState());

    runThroughBlazeTestCheckBox.setSelected(state.isRunThroughBlaze());

    updateButtonsAndLabelComponents(state.getTestingType());
    packageComponent.getComponent().setText(state.getPackageName());
    classComponent.getComponent().setText(state.getClassName());
    methodComponent.getComponent().setText(state.getMethodName());
    runnerComponent.getComponent().setText(state.getInstrumentationRunnerClass());
  }

  @Override
  public JComponent createComponent() {
    return UiUtil.createBox(commonStateEditor.createComponent(), panel);
  }

  @Override
  public void setComponentEnabled(boolean enabled) {
    componentEnabled = enabled;
    updateEnabledState();
  }

  private void updateEnabledState() {
    commonStateEditor.setComponentEnabled(componentEnabled);
    allInPackageButton.setEnabled(componentEnabled);
    classButton.setEnabled(componentEnabled);
    testMethodButton.setEnabled(componentEnabled);
    allInTargetButton.setEnabled(componentEnabled);
    packageComponent.setEnabled(componentEnabled);
    classComponent.setEnabled(componentEnabled);
    methodComponent.setEnabled(componentEnabled);
    runnerComponent.setEnabled(componentEnabled);
    labelTest.setEnabled(componentEnabled);
    runThroughBlazeTestCheckBox.setEnabled(componentEnabled);
    for (JComponent button : testingType2RadioButton) {
      if (button != null) {
        button.setEnabled(componentEnabled);
      }
    }
  }
}
