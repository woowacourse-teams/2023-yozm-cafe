/// <reference types="@types/gtag.js" />

declare global {
  interface Window {
    dataLayer: unknown[];
  }
}

const ID = process.env.GOOGLE_ANALYTICS_TRACKING_ID ?? null;

// eslint-disable-next-line @typescript-eslint/no-empty-function
const noop = () => {};

type GAEvent =
  | { name: 'share'; params: { cafeName: string } }
  | { name: 'click_like_button'; params: { cafeName: string } }
  | { name: 'click_menu_button'; params: { cafeName: string } }
  | { name: 'click_detail_button'; params: { cafeName: string } }
  | { name: 'cafe_view'; params: { cafeName: string } };

type GAConfig = {
  user_id: string;
};

export function withGAEvent<EventName extends GAEvent['name']>(
  ...args: Extract<GAEvent, { name: EventName }> extends { params: infer Params extends Record<string, unknown> }
    ? [event: EventName, params: Params]
    : [event: EventName]
): () => void;

export function withGAEvent<EventName extends GAEvent['name'], Args extends unknown[], ReturnType>(
  ...args: Extract<GAEvent, { name: EventName }> extends { params: infer Params extends Record<string, unknown> }
    ? [event: EventName, params: Params, fn: (...args: Args) => ReturnType]
    : [event: EventName, fn: (...args: Args) => ReturnType]
): (...args: Args) => ReturnType;

/**
 * Google Analytics 이벤트를 발생시킬 때 사용하는 함수
 *
 * 원본 함수를 마지막 인자로 넣어 이벤트 함수와 결합하여 사용 가능합니다
 */
export function withGAEvent<EventName extends GAEvent['name'], Args extends unknown[] = [], ReturnType = void>(
  ...args: Extract<GAEvent, { name: EventName }> extends { params: infer Params extends Record<string, unknown> }
    ? [event: EventName, params: Params] | [event: EventName, params: Params, fn: (...args: Args) => ReturnType]
    : [event: EventName] | [event: EventName, fn: (...args: Args) => ReturnType]
) {
  const [arg1, arg2, arg3] = args;
  const event = arg1;
  const params = typeof arg2 === 'object' ? arg2 : {};
  const fn = arg3 ?? noop;

  return (...args: Args) => {
    window.gtag('event', event, params);

    return fn(...args);
  };
}

export function setGAConfig(config: Partial<GAConfig>) {
  if (!ID) return;
  window.gtag('config', ID, config);
}
