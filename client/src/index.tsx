import ReactDOM from 'react-dom/client';
import App from './App';
import { MSW } from './environment';
import { worker } from './mocks/worker';

const main = async () => {
  if (MSW) {
    await worker.start();
  }

  ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(<App />);
};

main();
