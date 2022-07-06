import * as vscode from 'vscode';
import { readFileSync } from 'fs';

export class Store {
    private items = [];
    public getItems(): string[] {
        return this.items;
    }
    private getContent(path: string): Promise<string> {
        return new Promise((res, rej) => {
            res(readFileSync(path, {encoding:"utf-8"}));
        });
    }
    public async loadItemsFromDisk(path: string) {
        const content = await this.getContent(path);
        const converted = JSON.parse(content);
        this.items = converted.map((x:any) => {
            let rtn = x.id;
            if (x.data !== undefined) {
                rtn += " " + x.data;
            }
            return rtn;
        });
    }

	public clearItems() {
        this.items = [];        
    }
}