export type StarCategory = 
  | 'Bintang Fajar' 
  | 'Bintang Kebaikan' 
  | 'Bintang Kesabaran' 
  | 'Bintang Raga' 
  | 'Bintang Fokus';

export interface StarNode {
  id: string;
  category: StarCategory;
  title: string;
  note: string;
  timestamp: string;
  x: number; // Normalized 0..1 coordinates on constellation
  y: number; // Normalized 0..1 coordinates on constellation
  activeHalo?: boolean;
}

export interface ConstellationRasi {
  id: string;
  name: string; // e.g. "Rasi Aquila Noctis"
  month: string; // e.g. "Nov 2026"
  totalTarget: number; // e.g. 25
  activeCount: number; // e.g. 18
  stars: StarNode[];
}

export interface SlackingPass {
  id: string;
  title: string;
  description: string;
  durationHours: number;
  costStars: number;
  unlocked: boolean;
  active: boolean;
}

export interface SecreChatMessage {
  id: string;
  sender: 'secre' | 'user';
  timestamp: string;
  text: string;
  badge?: string;
  starAwarded?: {
    category: StarCategory;
    title: string;
  };
}

export interface SpiritualVaultStats {
  bintangDiraih: number;
  tiketMalas: number;
  rekorRasiHari: number;
  ikatanAstrum: number;
  ikatanLevel: number;
}
