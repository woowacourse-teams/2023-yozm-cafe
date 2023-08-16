import type { Meta, StoryObj } from '@storybook/react';
import { cafeMenus } from '../data/mockData';
import ImageModal from './ImageModal';

type Story = StoryObj<typeof ImageModal>;

const meta: Meta<typeof ImageModal> = {
  title: 'ImageModal',
  component: ImageModal,
};

export default meta;

export const Default: Story = {
  args: {
    imageUrls: cafeMenus[0].menuBoards.map((menuBoard) => menuBoard.imageUrl),
  },
};
