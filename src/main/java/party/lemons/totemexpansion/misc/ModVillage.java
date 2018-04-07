package party.lemons.totemexpansion.misc;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import party.lemons.totemexpansion.config.ModConfig;
import party.lemons.totemexpansion.config.ModConstants;
import party.lemons.totemexpansion.item.ModItems;

import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 6/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModVillage
{
	@GameRegistry.ObjectHolder(ModConstants.MODID + ":witchdoctor")
	public static VillagerRegistry.VillagerProfession WITCH_DOCTOR = null;


	@SubscribeEvent
	public static void onRegisterVillage(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event)
	{
		VillagerRegistry.VillagerProfession proff=  new VillagerRegistry.VillagerProfession(
				ModConstants.MODID + ":witchdoctor",
				ModConstants.MODID + ":textures/entity/villager/witch_doctor.png",
				ModConstants.MODID + ":textures/entity/villager/witch_doctor_zombie.png");

		event.getRegistry().register(
				proff
		);

		VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(proff, "witch_doctor");
		addTrade(career, ModItems.TOTEM_BASE, new EntityVillager.PriceInfo(1, 5));
		addTrade(career, ModItems.TOTEM_HEAD_LAVA, new EntityVillager.PriceInfo(4, 12));
		addTrade(career, ModItems.TOTEM_HEAD_PLUMMETING, new EntityVillager.PriceInfo(5, 15));
		addTrade(career, ModItems.TOTEM_HEAD_UNDYING, new EntityVillager.PriceInfo(6, 12));
		addTrade(career, ModItems.TOTEM_HEAD_BREATHING, new EntityVillager.PriceInfo(6, 12));
		addTrade(career, ModItems.TOTEM_HEAD_REPAIR, new EntityVillager.PriceInfo(6, 12));
		addSkullTrade(career, 0, new EntityVillager.PriceInfo(15, 39));
		addSkullTrade(career, 1, new EntityVillager.PriceInfo(15, 39));
		addSkullTrade(career, 4, new EntityVillager.PriceInfo(15, 39));

		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandler());
		MapGenStructureIO.registerStructureComponent(WitchDoctorHouse.class, ModConstants.MODID + ":witchdoctor");
	}

	public static void addTrade(VillagerRegistry.VillagerCareer career, Item item, EntityVillager.PriceInfo info)
	{
		for(String s : ModConfig.TRADE_BLACKLIST_HEADS)
		{
			if(s.equalsIgnoreCase(item.getRegistryName().toString()))
				return;
		}

		career.addTrade(1, new EntityVillager.ListItemForEmeralds(item, info));
	}

	public static void addSkullTrade(VillagerRegistry.VillagerCareer career, int meta, EntityVillager.PriceInfo info)
	{
		for(int i : ModConfig.TRADE_BLACKLIST_SKULLS)
		{
			if(i == meta)
				return;
		}

		career.addTrade(1, new EntityVillager.ListItemForEmeralds(new ItemStack(Items.SKULL, 1, meta), info));
	}

	private static class VillageCreationHandler implements VillagerRegistry.IVillageCreationHandler
	{
		@Override
		public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i)
		{
			return new StructureVillagePieces.PieceWeight(WitchDoctorHouse.class, 3, 1);
		}

		@Override
		public Class<?> getComponentClass()
		{
			return WitchDoctorHouse.class;
		}

		@Override
		public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5)
		{
			return WitchDoctorHouse.createPiece(startPiece, pieces, random, p1, p2, p3, facing, p5);
		}
	}

	public static class WitchDoctorHouse extends StructureVillagePieces.Village
	{
		public WitchDoctorHouse()
		{
		}

		public WitchDoctorHouse(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = p_i45571_4_;
		}

		public static WitchDoctorHouse createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null ? new WitchDoctorHouse(start, p_175850_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
			}

			IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.STONEBRICK.getDefaultState());
			IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.BIRCH_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
			IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.BIRCH_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
			IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.BIRCH_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
			IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState()).withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
			IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.BIRCH_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);

			for (int i = -1; i <= 2; ++i)
			{
				for (int j = 0; j <= 8; ++j)
				{
					this.setBlockState(worldIn, iblockstate1, j, 6 + i, i, structureBoundingBoxIn);
					this.setBlockState(worldIn, iblockstate2, j, 6 + i, 5 - i, structureBoundingBoxIn);
				}
			}

			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
			this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
			this.setBlockState(worldIn, iblockstate4, 7, 1, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, iblockstate3, 7, 1, 3, structureBoundingBoxIn);
			this.setBlockState(worldIn, iblockstate1, 6, 1, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, iblockstate1, 5, 1, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, iblockstate1, 4, 1, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, iblockstate1, 3, 1, 4, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);

			if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
			{
				this.setBlockState(worldIn, iblockstate5, 1, 0, -1, structureBoundingBoxIn);

				if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
				{
					this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
				}
			}

			for (int l = 0; l < 6; ++l)
			{
				for (int k = 0; k < 9; ++k)
				{
					this.clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
					this.replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
				}
			}

			this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
			return true;
		}

		@Override
		protected VillagerRegistry.VillagerProfession chooseForgeProfession(int count, VillagerRegistry.VillagerProfession prof)
		{
			return WITCH_DOCTOR;
		}
	}
}
