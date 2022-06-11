package com.shabashab.graphplotter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class ImGuiHelper {
  private static long _windowHandle;
  private static ImGuiImplGlfw _imGuiGlfw;
  private static ImGuiImplGl3 _imGuiGl3;

  public static long getWindowHandle() {
    return _windowHandle;
  }

  public static ImGuiImplGlfw getImGuiGlfw() {
    return _imGuiGlfw;
  }

  public static ImGuiImplGl3 getImGuiGl3() {
    return _imGuiGl3;
  }

  public static void initializeImGui() {
    _imGuiGlfw = new ImGuiImplGlfw();
    _imGuiGl3 = new ImGuiImplGl3();

    // create 3D scene
    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit())
    {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
    ImGui.createContext();
    final ImGuiIO io = ImGui.getIO();
    io.setIniFilename(null);

    _windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

    _imGuiGlfw.init(_windowHandle, true);
    _imGuiGl3.init();
  }
}
