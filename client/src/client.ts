import { AuthProvider, Cafe, User } from './types';

export class ClientNetworkError extends Error {
  constructor() {
    super('인터넷에 연결할 수 없습니다');
  }
}

class Client {
  accessToken: string | null = null;

  async fetch(input: RequestInfo | URL, init?: RequestInit): Promise<Response> {
    try {
      const response = await fetch(`/api${input}`, {
        ...init,
        headers: {
          ...init?.headers,
          ...(this.accessToken ? { Authorization: `Bearer ${this.accessToken}` } : {}),
        },
      });
      if (!response.ok) {
        throw response;
      }
      return response;
    } catch (error) {
      throw new ClientNetworkError();
    }
  }

  async fetchJson<TData>(input: RequestInfo | URL, init?: RequestInit): Promise<TData> {
    return this.fetch(input, init).then((response) => response.json());
  }

  setAccessToken(accessToken: string | null) {
    this.accessToken = accessToken;
  }

  getCafes(page = 1) {
    return this.fetchJson<Cafe[]>(`/cafes?page=${page}`);
  }

  getCafesForGuest(page = 1) {
    return this.fetchJson<Cafe[]>(`/cafes/guest?page=${page}`);
  }

  getUser(userId: string) {
    return this.fetchJson<User>(`/members/${userId}`);
  }

  async setLikedCafe(cafeId: Cafe['id'], isLiked: Cafe['isLiked']) {
    await this.fetch(`/cafes/${cafeId}/likes?isLiked=${isLiked}`, { method: 'POST' });
  }

  addFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetchJson<void>(`/cafes/${cafeId}/likes`, { method: 'POST' });
  }

  removeFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetchJson<void>(`/cafes/${cafeId}/likes`, { method: 'DELETE' });
  }

  /**
   * 인증 수행 시, OAuth 제공자(provider)와 인증 코드(Authorization Code) 값을
   * 백엔드에 전송하면 백엔드에서 발급한 accessToken을 응답으로 받을 수 있다.
   */
  issueAccessToken(provider: AuthProvider, code: string) {
    return this.fetchJson<{ token: string }>(`/auth/${provider}?code=${code}`, { method: 'POST' }).then(
      (data) => data.token,
    );
  }

  /**
   * access token을 지워도 refresh token이 남아있으면 계속 access token이 발급되기
   * 때문에, 완전한 로그아웃을 하려면 refresh token을 삭제해주어야 한다.
   *
   * refresh token은 httpOnly 쿠키로 저장되기 때문에 서버에 요청을 하여 쿠키를 삭제
   * 해주어야 한다.
   */
  async clearRefreshToken() {
    await this.fetch(`/auth`, { method: 'DELETE' });
  }
}

const client = new Client();

export default client;
