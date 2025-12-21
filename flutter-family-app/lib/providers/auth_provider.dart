import 'package:flutter_riverpod/flutter_riverpod.dart';

/// 认证提供者 - 管理用户认证状态
final authProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) {
  return AuthNotifier();
});

class AuthState {
  final String? accessToken;
  final String? refreshToken;
  final String? userId;
  final bool isLoading;
  final String? error;

  AuthState({
    this.accessToken,
    this.refreshToken,
    this.userId,
    this.isLoading = false,
    this.error,
  });

  AuthState copyWith({
    String? accessToken,
    String? refreshToken,
    String? userId,
    bool? isLoading,
    String? error,
  }) {
    return AuthState(
      accessToken: accessToken ?? this.accessToken,
      refreshToken: refreshToken ?? this.refreshToken,
      userId: userId ?? this.userId,
      isLoading: isLoading ?? this.isLoading,
      error: error ?? this.error,
    );
  }
}

class AuthNotifier extends StateNotifier<AuthState> {
  AuthNotifier() : super(AuthState());

  Future<void> login(String phone, String password) async {
    state = state.copyWith(isLoading: true, error: null);
    try {
      // TODO: 调用API进行登录
      // 模拟登录
      await Future.delayed(const Duration(seconds: 2));
      state = state.copyWith(
        isLoading: false,
        accessToken: 'mock_token',
        userId: '1',
      );
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: e.toString(),
      );
    }
  }

  Future<void> register(String phone, String nickname, String password) async {
    state = state.copyWith(isLoading: true, error: null);
    try {
      // TODO: 调用API进行注册
      // 模拟注册
      await Future.delayed(const Duration(seconds: 2));
      state = state.copyWith(isLoading: false);
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: e.toString(),
      );
    }
  }

  void logout() {
    state = AuthState();
  }
}
