import { Cafe } from './types';

export class ClientNetworkError extends Error {
  constructor() {
    super('인터넷에 연결할 수 없습니다');
  }
}

class Client {
  accessToken: string | null = null;

  async fetch<TData>(input: RequestInfo | URL, init?: RequestInit): Promise<TData> {
    try {
      const response = await fetch(input, {
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

  getCafes() {
    return this.fetch<Cafe[]>('/cafes');
  }

  addFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetch<void>(`/cafes/${cafeId}/likes`, { method: 'POST' });
  }

  removeFavoriteCafe(cafeId: Cafe['id']) {
    return this.fetch<void>(`/cafes/${cafeId}/likes`, { method: 'DELETE' });
  }
}

const client = new Client();

export default client;
