import { AuthProvider, Cafe } from './types';

export class ClientNetworkError extends Error {
  constructor() {
    super('인터넷에 연결할 수 없습니다');
  }
}

class Client {
  accessToken: string | null = null;

  async fetch<TData>(input: RequestInfo | URL, init?: RequestInit): Promise<TData> {
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
      return response.json();
    } catch (error) {
      throw new ClientNetworkError();
    }
  }

  setAccessToken(accessToken: string | null) {
    this.accessToken = accessToken;
  }

  getCafes(page = 1) {
    return this.fetch<Cafe[]>(`/cafes?page=${page}`);
  }

  addFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetch<void>(`/cafes/${cafeId}/likes`, { method: 'POST' });
  }

  removeFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetch<void>(`/cafes/${cafeId}/likes`, { method: 'DELETE' });
  }

  /**
   * 인증 수행 시, OAuth 제공자(provider)와 인증 코드(Authorization Code) 값을
   * 백엔드에 전송하면 백엔드에서 발급한 accessToken을 응답으로 받을 수 있다.
   */
  issueAccessToken(provider: AuthProvider, code: string) {
    return this.fetch<{ token: string }>(`/auth/${provider}?code=${code}`, { method: 'POST' }).then(
      (data) => data.token,
    );
  }
}

const client = new Client();

export default client;
