'use client'

import { useState } from 'react'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { ProductsTab } from '@/components/products-tab'
import { CategoriesTab } from '@/components/categories-tab'
import { MovementsTab } from '@/components/movements-tab'
import { Package, Sun, Moon } from 'lucide-react'
import { Button } from '@/components/ui/button'

export default function InventoryPage() {
  const [activeTab, setActiveTab] = useState('products')
  const [theme, setTheme] = useState<'light' | 'dark'>('dark')

  const toggleTheme = () => {
    const newTheme = theme === 'light' ? 'dark' : 'light'
    setTheme(newTheme)
    document.documentElement.classList.toggle('dark', newTheme === 'dark')
  }

  return (
    <div className="min-h-screen bg-background">
      <div className="border-b border-border">
        <div className="container mx-auto px-6 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex items-center justify-center w-10 h-10 rounded-lg bg-accent">
                <Package className="w-6 h-6 text-accent-foreground" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-foreground">Sistema de Inventário</h1>
                <p className="text-sm text-muted-foreground">Gerencie produtos, categorias e movimentações</p>
              </div>
            </div>
            
            <Button
              variant="outline"
              size="icon"
              onClick={toggleTheme}
              className="rounded-lg"
            >
              {theme === 'light' ? (
                <Moon className="w-5 h-5" />
              ) : (
                <Sun className="w-5 h-5" />
              )}
            </Button>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-6 py-8">
        <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
          <TabsList className="grid w-full max-w-md grid-cols-3 mb-8">
            <TabsTrigger value="products">Produtos</TabsTrigger>
            <TabsTrigger value="categories">Categorias</TabsTrigger>
            <TabsTrigger value="movements">Movimentações</TabsTrigger>
          </TabsList>

          <TabsContent value="products" className="mt-0">
            <ProductsTab />
          </TabsContent>

          <TabsContent value="categories" className="mt-0">
            <CategoriesTab />
          </TabsContent>

          <TabsContent value="movements" className="mt-0">
            <MovementsTab />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  )
}
