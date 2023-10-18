import APIError from './errors/APIError';
import NetworkError from './errors/NetworkError';
import type {
  AuthProvider,
  AuthUrl,
  Cafe,
  CafeMapLocation,
  CafeMenu,
  LikedCafe,
  MapBounds,
  Rank,
  SearchedCafe,
  User,
} from './types';

class Client {
  isAccessTokenRefreshing = false;

  accessToken: string | null = null;

  accessTokenRefreshListener: ((accessToken: string | null) => void) | null = null;

  // FIXME: react <-> 외부 시스템(Client)와 상태 연동을 위해 양방향 바인딩을 걸었습니다.
  // 향후 단방향으로 수정해야 합니다.
  onAccessTokenRefresh(listener: (accessToken: string | null) => void) {
    this.accessTokenRefreshListener = listener;
  }

  async fetch(input: RequestInfo | URL, init?: RequestInit): Promise<Response> {
    const fetchFn: () => Promise<Response> = () =>
      fetch(`/api${input}`, {
        ...init,
        headers: {
          ...init?.headers,
          ...(this.accessToken ? { Authorization: `Bearer ${this.accessToken}` } : {}),
        },
      });
    let response: Response;

    try {
      response = await fetchFn();
    } catch (error) {
      throw new NetworkError();
    }

    if (!response.ok) {
      if (response.status === 401 && !this.isAccessTokenRefreshing) {
        // this.refreshAccessToken() 을 호출하기 전,
        // this.isAccessTokenRefreshing을 true로 설정해줘야 잠재적인 recursion loop를 방지할 수 있습니다.
        this.isAccessTokenRefreshing = true;
        const accessToken = await this.refreshAccessToken();
        this.accessToken = accessToken;
        this.accessTokenRefreshListener?.(accessToken);
        this.isAccessTokenRefreshing = false;

        response = await fetchFn();
      }

      if (!response.ok) {
        // access token 재발급이 불가하기 때문에 access token을 삭제한다.
        this.accessTokenRefreshListener?.(null);
        let body: unknown;
        try {
          body = await response.json();
        } catch (error) {
          body = await response.text();
        }
        throw new APIError(response, body);
      }
    }
    return response;
  }

  async fetchJson<TData>(input: RequestInfo | URL, init?: RequestInit): Promise<TData> {
    return this.fetch(input, init).then((response) => response.json());
  }

  setAccessToken(accessToken: string | null) {
    this.accessToken = accessToken;
  }

  getAuthUrls() {
    return this.fetchJson<AuthUrl[]>(`/auth/urls`);
  }

  getCafes() {
    return this.fetchJson<Cafe[]>(`/cafes`);
  }

  getCafe(cafeId: Cafe['id']) {
    return this.fetchJson<Cafe>(`/cafes/${cafeId}`);
  }

  getCafeRank(page = 1) {
    return this.fetchJson<Rank[]>(`/cafes/ranks?page=${page}`);
  }

  getCafesForGuest(page = 1) {
    return this.fetchJson<Cafe[]>(`/cafes/guest?page=${page}`);
  }

  getUser(userId: string) {
    return this.fetchJson<User>(`/members/${userId}`);
  }

  getLikedCafeList(userId: string, page = 1) {
    return this.fetchJson<LikedCafe[]>(`/members/${userId}/liked-cafes?page=${page}`);
  }

  getLikedCafeDetail(userId: string) {
    return this.fetchJson<Cafe[]>(`/members/${userId}/liked-cafes/details`);
  }

  async setLikedCafe(cafeId: Cafe['id'], isLiked: Cafe['isLiked']) {
    await this.fetch(`/cafes/${cafeId}/likes?isLiked=${isLiked}`, { method: 'POST' });
  }

  getCafeMenu(cafeId: Cafe['id']) {
    return this.fetchJson<CafeMenu>(`/cafes/${cafeId}/menus`);
  }

  searchCafes(searchParams: { name: string; menu?: string; address?: string }) {
    const sanitizedSearchParams = Object.fromEntries(
      Object.entries({
        cafeName: searchParams.name.trim(),
        menu: searchParams.menu?.trim() ?? '',
        address: searchParams.address?.trim() ?? '',
      }).filter(([, value]) => (value?.length ?? 0) >= 2),
    );
    if (Object.keys(sanitizedSearchParams).length === 0) {
      return Promise.resolve([]);
    }
    return this.fetchJson<SearchedCafe[]>(`/cafes/search?${new URLSearchParams(sanitizedSearchParams).toString()}`);
  }

  getCafesNearLocation(
    longitude: MapBounds['longitude'],
    latitude: MapBounds['latitude'],
    longitudeDelta: MapBounds['longitudeDelta'],
    latitudeDelta: MapBounds['latitudeDelta'],
  ) {
    return this.fetchJson<CafeMapLocation[]>(
      `/cafes/location?longitude=${longitude}&latitude=${latitude}&longitudeDelta=${longitudeDelta}&latitudeDelta=${latitudeDelta}`,
    );
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
   * accessToken이 만료되었을 때, 서버에서 accessToken을 다시 발급받는다.
   */
  refreshAccessToken() {
    return this.fetchJson<{ token: string }>(`/auth`).then((data) => data.token);
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
