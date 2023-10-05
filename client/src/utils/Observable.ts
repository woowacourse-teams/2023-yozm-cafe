type Subscriber = () => void;

class Observable<T> {
  protected subscribers: Subscriber[] = [];

  protected state: T;

  constructor(initialState: T) {
    this.state = initialState;
  }

  subscribe(subscriber: Subscriber) {
    this.subscribers.push(subscriber);
  }

  unsubscribe(subscriber: Subscriber) {
    const foundIndex = this.subscribers.indexOf(subscriber);
    if (foundIndex === -1) return;
    this.subscribers.splice(foundIndex, 1);
  }

  emit() {
    this.subscribers.forEach((subscriber) => subscriber());
  }

  getState() {
    return this.state;
  }

  setState(state: T) {
    this.state = state;
    this.emit();
  }
}

export default Observable;
