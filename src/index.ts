import { registerPlugin } from '@capacitor/core';

import type { DantSuPlugin } from './definitions';

const DantSu = registerPlugin<DantSuPlugin>('DantSu', {
  web: () => import('./web').then((m) => new m.DantSuWeb()),
});

export * from './definitions';
export { DantSu };
