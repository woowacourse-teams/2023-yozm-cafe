import ReactDOM from 'react-dom/client';
import App from './App';
import { MSW } from './environment';
import { worker } from './mocks/worker';

declare global {
  interface Document {
    readonly bodyRoot: HTMLDivElement;
  }
}

Object.assign(document, { bodyRoot: document.getElementById('root') });

const main = async () => {
  if (MSW) {
    await worker.start();
  }

  ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(<App />);
};

main();
