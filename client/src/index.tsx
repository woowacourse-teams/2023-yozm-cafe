import ReactDOM from 'react-dom/client';
import App from './App';
import { MSW } from './environment';

declare global {
  interface Document {
    /**
     * 앱에서 사용하는 (가상) 뷰포트입니다.
     * html에서는 `<div id="root"></div>` 을 가리킵니다.
     *
     * ![](https://github.com/woowacourse-teams/2023-yozm-cafe/assets/20203944/d5be8666-7e7c-4b85-a561-918d0c4c0d40)
     */
    readonly bodyRoot: HTMLDivElement;
  }
}

Object.assign(document, { bodyRoot: document.getElementById('root') });

const main = async () => {
  if (MSW) {
    const { default: worker } = await import('./mocks/worker');
    await worker.start();
  }

  ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(<App />);
};

main();
